package com.alibaba.simpleEL.dialect.tiny;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.simpleEL.ELException;
import com.alibaba.simpleEL.JavaSource;
import com.alibaba.simpleEL.TypeUtils;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELBinaryOperator;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELIdentifierExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELLiteralExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELMethodInvokeExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELPropertyExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELStringExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyELVariantRefExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.TinyUnaryOpExpr;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELForEachStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELForStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyELStatement;
import com.alibaba.simpleEL.dialect.tiny.ast.stmt.TinyLocalVarDeclareStatement;
import com.alibaba.simpleEL.dialect.tiny.parser.TinyELExprParser;
import com.alibaba.simpleEL.dialect.tiny.parser.TinyStatementParser;
import com.alibaba.simpleEL.dialect.tiny.visitor.TinyELOutputVisitor;
import com.alibaba.simpleEL.preprocess.TemplatePreProcessor;

public class TinyELPreprocessor extends TemplatePreProcessor {

    public final static String        DEFAULT_PACKAGE_NAME = "com.alibaba.simpleEL.dialect.tiny.gen";

    private final Map<String, Method> functions            = new HashMap<String, Method>();

    public TinyELPreprocessor(){
        this.packageName = DEFAULT_PACKAGE_NAME;
    }

    public Map<String, Method> getFunctions() {
        return this.functions;
    }

    @Override
    public JavaSource handle(Map<String, Object> context, String text) {
        if (variantResolver == null) {
            throw new IllegalStateException("variantResolver is null");
        }

        String resolvedResult;
        if (!allowMultiStatement) {
            TinyELExprParser parser = new TinyELExprParser(text);
            TinyELExpr expr = parser.expr();

            StringWriter out = new StringWriter();
            TinyELOutputVisitor visitor = createVisitor(out);
            expr.accept(visitor);

            resolvedResult = out.toString();
        } else {
            TinyStatementParser parser = new TinyStatementParser(text);
            List<TinyELStatement> statements = parser.statementList();

            StringWriter out = new StringWriter();
            TinyELOutputVisitor visitor = createVisitor(out);

            visitor.incrementIndent();
            for (TinyELStatement statement : statements) {
                visitor.println();
                statement.accept(visitor);
            }
            visitor.decrementIndent();

            resolvedResult = out.toString();
        }

        // System.out.println(resolvedResult);

        if (!allowMultiStatement) {
            resolvedResult = "return " + resolvedResult + ";";
        }

        final String className = "GenClass_" + digits();

        String source = template.replace("$packageName", packageName)//
        .replace("$className", className)//
        .replace("$expression", resolvedResult);

        JavaSource javaSource = new JavaSource(packageName, className, source);

        return javaSource;
    }

    protected TinyELOutputVisitor createVisitor(StringWriter out) {
        JavaSourceGenVisitor visitor = new JavaSourceGenVisitor(new PrintWriter(out));
        return visitor;
    }

    public class JavaSourceGenVisitor extends TinyELOutputVisitor {

        protected Map<String, Object> localVariants = new HashMap<String, Object>();

        public JavaSourceGenVisitor(PrintWriter out){
            super(out);
        }

        @Override
        public boolean visit(TinyELIdentifierExpr x) {
            String ident = x.getName();

            if (localVariants.containsKey(ident)) {
                out.print(ident);
                return false;
            }

            {
                String fullName = "java.lang." + ident;
                if (isClassName(fullName)) {
                    out.print(ident);
                    return false;
                }
            }
            {
                String fullName = "java.util." + ident;
                if (isClassName(fullName)) {
                    out.print(fullName);
                    return false;
                }
            }

            String result = variantResolver.resolve(ident);
            out.print(result);
            return false;
        }

        @Override
        public boolean visit(TinyELVariantRefExpr x) {
            String ident = x.getName();
            String result = variantResolver.resolve(ident);
            out.print(result);
            return false;
        }

        public boolean isClassName(String fullName) {
            try {
                Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(fullName);

                if (clazz != null) {
                    return true;
                }
            } catch (ClassNotFoundException e) {
                return false;
            }

            return false;
        }

        @Override
        public boolean visit(TinyELPropertyExpr x) {
            if (x.getOwner() instanceof TinyELBinaryOpExpr) {
                out.print('(');
                x.getOwner().accept(this);
                out.print(')');
                out.print(".");
                out.print(x.getName());

                return false;
            }

            String fullName = x.toString();
            if (isClassName(fullName)) {
                out.print(fullName);
                return false;
            }

            x.getOwner().accept(this);

            out.print(".");
            out.print(x.getName());
            return false;
        }

        private Class<?> getType(TinyELExpr expr) {
            if (expr instanceof TinyELIdentifierExpr) {
                TinyELIdentifierExpr ident = (TinyELIdentifierExpr) expr;
                String name = ident.getName();

                Class<?> type = variantResolver.getType(name);
                if (type != null) {
                    return type;
                }

            }
            
            if (expr instanceof TinyELVariantRefExpr) {
                TinyELVariantRefExpr ident = (TinyELVariantRefExpr) expr;
                String name = ident.getName();

                Class<?> type = variantResolver.getType(name);
                if (type != null) {
                    return type;
                }

            }
            
            if (expr instanceof TinyELBinaryOpExpr) {
                TinyELBinaryOpExpr binaryExpr = (TinyELBinaryOpExpr) expr;
                Class<?> leftType = getType(binaryExpr.getLeft());
                Class<?> rightType = getType(binaryExpr.getRight());
                
                if (leftType != null && leftType.equals(rightType)) {
                    return leftType;
                }
            }
            
            return null;
        }

        @Override
        public boolean visit(TinyELMethodInvokeExpr x) {
            if (x.getOwner() == null) {
                String methodName = x.getMethodName();
                Method method = functions.get(methodName);

                if (method == null) {
                    return super.visit(x);
                }

                Class<?>[] types = method.getParameterTypes();
                if (!method.isVarArgs()) {
                    if (types.length != x.getParameters().size()) {
                        return super.visit(x);
                    }

                    String className = method.getDeclaringClass().getName();
                    className = className.replaceAll("\\$", "."); // inner class
                    out.print(className);
                    out.print(".");
                    out.print(method.getName());

                    out.print("(");
                    for (int i = 0, size = x.getParameters().size(); i < size; ++i) {
                        if (i != 0) {
                            out.print(",");
                        }

                        TinyELExpr param = x.getParameters().get(i);
                        if (types[i] == getType(param)) {
                            param.accept(this);
                        } else {
                            visitMethodParameter(types[i], param);
                        }
                    }

                    out.print(")");
                    return false;
                } else {
                    if (types.length > x.getParameters().size()) {
                        return super.visit(x);
                    }

                    String className = method.getDeclaringClass().getName();
                    className = className.replaceAll("\\$", "."); // inner class
                    out.print(className);
                    out.print(".");
                    out.print(method.getName());

                    out.print("(");
                    for (int i = 0, size = x.getParameters().size(); i < size; ++i) {
                        if (i != 0) {
                            out.print(",");
                        }

                        Class<?> type;
                        if (i >= types.length) {
                            type = types[types.length - 1];
                        } else {
                            type = types[i];
                        }
                        visitMethodParameter(type, x.getParameters().get(i));
                    }

                    out.print(")");
                }
            }

            if (x.getOwner() instanceof TinyELIdentifierExpr && x.getMethodName().equals("equals")
                && x.getParameters().size() == 1) {
                TinyELIdentifierExpr leftIdent = (TinyELIdentifierExpr) x.getOwner();
                String varName = leftIdent.getName();

                TinyELExpr param = x.getParameters().get(0);
                Class<?> type = variantResolver.getType(varName);
                if (type == Date.class && param instanceof TinyELStringExpr) {
                    x.getOwner().accept(this);
                    print(".equals(_date(");
                    param.accept(this);
                    print("))");
                    return false;
                }
            }

            return super.visit(x);
        }

        public void visitMethodParameter(Class<?> type, TinyELExpr expr) {
            if (type == String.class && expr instanceof TinyELStringExpr) {
                expr.accept(this);
                return;
            }

            if (boolean.class == type) {
                out.print("_bool(");
                expr.accept(this);
                out.print(")");
            } else if (String.class == type) {
                out.print("_string(");
                expr.accept(this);
                out.print(")");
            } else if (byte.class == type) {
                out.print("_byte(");
                expr.accept(this);
                out.print(")");
            } else if (short.class == type) {
                out.print("_short(");
                expr.accept(this);
                out.print(")");
            } else if (int.class == type) {
                out.print("_int(");
                expr.accept(this);
                out.print(")");
            } else if (long.class == type) {
                out.print("_long(");
                expr.accept(this);
                out.print(")");
            } else if (float.class == type) {
                out.print("_float(");
                expr.accept(this);
                out.print(")");
            } else if (double.class == type) {
                out.print("_double(");
                expr.accept(this);
                out.print(")");
            } else if (BigInteger.class == type) {
                out.print("_bigInt(");
                expr.accept(this);
                out.print(")");
            } else if (BigDecimal.class == type) {
                out.print("_decimal(");
                expr.accept(this);
                out.print(")");
            } else if (java.util.Date.class == type) {
                out.print("_date(");
                expr.accept(this);
                out.print(")");
            } else if (Object.class == type) {
                expr.accept(this);
            } else {
                String className = TypeUtils.getClassName(type);

                out.print("(" + className + ")");
                expr.accept(this);
            }

        }

        @Override
        public boolean visit(TinyELBinaryOpExpr x) {
            x.getLeft().setParentExpr(x);
            x.getRight().setParentExpr(x);

            if (getType(x.getLeft()) == String.class || getType(x.getRight()) == String.class) {
                out.print("(");
                x.getLeft().accept(this);
                out.print(" + ");
                x.getRight().accept(this);
                out.print(")");
                return false;
            }

            if (x.getLeft() instanceof TinyELIdentifierExpr) {
                TinyELIdentifierExpr leftIdent = (TinyELIdentifierExpr) x.getLeft();
                String varName = leftIdent.getName();
                if (!localVariants.containsKey(varName)) {
                    switch (x.getOperator()) {
                        case Assignment:
                            print("ctx.put(\"");
                            print(varName);
                            print("\", ");
                            x.getRight().accept(this);
                            print(")");
                            return false;
                        case AddAndAssignment:
                            print("ctx.put(\"");
                            print(varName);
                            print("\", ");
                            leftIdent.accept(this);
                            print(" ");
                            print(TinyELBinaryOperator.Add.name);
                            print(" ");
                            x.getRight().accept(this);
                            print(")");
                            return false;
                        default:
                            break;
                    }
                }

                Class<?> type = variantResolver.getType(varName);
                if (type == BigDecimal.class) {
                    switch (x.getOperator()) {
                        case Add:
                            x.getLeft().accept(this);
                            print(".add(_decimal(");
                            x.getRight().accept(this);
                            print("))");
                            return false;
                        case Subtract:
                            x.getLeft().accept(this);
                            print(".subtract(_decimal(");
                            x.getRight().accept(this);
                            print("))");
                            return false;
                        case GreaterThan:
                            x.getLeft().accept(this);
                            print(".compareTo(_decimal(");
                            x.getRight().accept(this);
                            print(")) > 0");
                            return false;
                        case GreaterThanOrEqual:
                            x.getLeft().accept(this);
                            print(".compareTo(_decimal(");
                            x.getRight().accept(this);
                            print(")) >= 0");
                            return false;
                        case LessThan:
                            x.getLeft().accept(this);
                            print(".compareTo(_decimal(");
                            x.getRight().accept(this);
                            print(")) < 0");
                            return false;
                        case LessThanOrEqual:
                            x.getLeft().accept(this);
                            print(".compareTo(_decimal(");
                            x.getRight().accept(this);
                            print(")) <= 0");
                            return false;
                        default:
                            break;
                    }
                }
            }

            if (x.getLeft() instanceof TinyELVariantRefExpr) {
                TinyELVariantRefExpr var = (TinyELVariantRefExpr) x.getLeft();
                String varName = var.getName();
                if (!localVariants.containsKey(varName)) {
                    switch (x.getOperator()) {
                        case Assignment:
                            print("ctx.put(\"");
                            print(varName);
                            print("\", ");
                            x.getRight().accept(this);
                            print(")");
                            return false;
                        case AddAndAssignment:
                            print("ctx.put(\"");
                            print(varName);
                            print("\", ");
                            var.accept(this);
                            print(" ");
                            print(TinyELBinaryOperator.Add.name);
                            print(" ");
                            x.getRight().accept(this);
                            print(")");
                            return false;
                        default:
                            break;
                    }
                }

                Class<?> type = variantResolver.getType(varName);
                if (type == BigDecimal.class) {
                    switch (x.getOperator()) {
                        case Add:
                            x.getLeft().accept(this);
                            print(".add(_decimal(");
                            x.getRight().accept(this);
                            print("))");
                            return false;
                        case GreaterThan:
                            x.getLeft().accept(this);
                            print(".compareTo(_decimal(");
                            x.getRight().accept(this);
                            print(")) > 0");
                            return false;
                        case GreaterThanOrEqual:
                            x.getLeft().accept(this);
                            print(".compareTo(_decimal(");
                            x.getRight().accept(this);
                            print(")) >= 0");
                            return false;
                        case LessThan:
                            x.getLeft().accept(this);
                            print(".compareTo(_decimal(");
                            x.getRight().accept(this);
                            print(")) < 0");
                            return false;
                        case LessThanOrEqual:
                            x.getLeft().accept(this);
                            print(".compareTo(_decimal(");
                            x.getRight().accept(this);
                            print(")) <= 0");
                            return false;
                        default:
                            break;
                    }
                }

            }

            if (x.getOperator() == TinyELBinaryOperator.InstanceOf) {
                if (x.getLeft() instanceof TinyELIdentifierExpr) {
                    TinyELIdentifierExpr ident = (TinyELIdentifierExpr) x.getLeft();
                    String varName = ident.getName();
                    if (!localVariants.containsKey(varName)) {
                        print("ctx.get(\"");
                        print(varName);
                        print("\") instanceof ");
                    }
                    x.getRight().accept(this);
                    return false;
                }
            }
            return super.visit(x);
        }

        @Override
        public boolean visit(TinyLocalVarDeclareStatement x) {
            for (int i = 0, size = x.getVariants().size(); i < size; ++i) {
                String varName = x.getVariants().get(i).getName();
                localVariants.put(varName, x.getType());
            }

            return super.visit(x);
        }

        @Override
        public boolean visit(TinyELForStatement x) {
            for (int i = 0, size = x.getVariants().size(); i < size; ++i) {
                String varName = x.getVariants().get(i).getName();
                localVariants.put(varName, x.getType());
            }

            return super.visit(x);
        }

        @Override
        public boolean visit(TinyELForEachStatement x) {
            localVariants.put(x.getVariant(), x.getType());
            return super.visit(x);
        }

        @Override
        public boolean visit(TinyUnaryOpExpr x) {
            TinyELExpr expr = x.getExpr();

            if (expr instanceof TinyELLiteralExpr) {
                return super.visit(x);
            }

            Class<?> type = getType(x.getExpr());

            if (type == BigDecimal.class) {
                switch (x.getOperator()) {
                    case Plus:
                        x.getExpr().accept(this);
                        return false;
                    case Minus:
                        if (type == BigDecimal.class) {
                            print("_decimal(");
                            x.getExpr().accept(this);
                            print(").negate()");
                        }
                        return false;
                    default:
                        break;
                }
            }

            if (expr instanceof TinyELIdentifierExpr) {
                TinyELIdentifierExpr identExpr = (TinyELIdentifierExpr) x.getExpr();
                String varName = identExpr.getName();

                if (localVariants.containsKey(varName)) {
                    return super.visit(x);
                }

                switch (x.getOperator()) {
                    case Plus:
                    case Minus:
                        return super.visit(x);
                    case PreIncrement:
                        print("putAndGet(ctx, \"");
                        print(varName);
                        print("\", ");
                        identExpr.accept(this);
                        print(" + 1");
                        print(")");
                        return false;
                    case PostIncrement:
                        print("ctx.put(\"");
                        print(varName);
                        print("\", ");
                        identExpr.accept(this);
                        print(" + 1");
                        print(")");
                        return false;
                    case PreDecrement:
                        print("putAndGet(ctx, \"");
                        print(varName);
                        print("\", ");
                        identExpr.accept(this);
                        print(" - 1");
                        print(")");
                        return false;
                    case PostDecrement:
                        print("ctx.put(\"");
                        print(varName);
                        print("\", ");
                        identExpr.accept(this);
                        print(" - 1");
                        print(")");
                        return false;
                    default:
                        throw new ELException("TOOD");
                }
            }

            if (x.getExpr() instanceof TinyELVariantRefExpr) {
                TinyELVariantRefExpr var = (TinyELVariantRefExpr) x.getExpr();
                String varName = var.getName();

                if (localVariants.containsKey(varName)) {
                    return super.visit(x);
                }

                switch (x.getOperator()) {
                    case Plus:
                    case Minus:
                        return super.visit(x);
                    case PreIncrement:
                        print("putAndGet(ctx, \"");
                        print(varName);
                        print("\", ");
                        var.accept(this);
                        print(" + 1");
                        print(")");
                        return false;
                    case PostIncrement:
                        print("ctx.put(\"");
                        print(varName);
                        print("\", ");
                        var.accept(this);
                        print(" + 1");
                        print(")");
                        return false;
                    case PreDecrement:
                        print("putAndGet(ctx, \"");
                        print(varName);
                        print("\", ");
                        var.accept(this);
                        print(" - 1");
                        print(")");
                        return false;
                    case PostDecrement:
                        print("ctx.put(\"");
                        print(varName);
                        print("\", ");
                        var.accept(this);
                        print(" - 1");
                        print(")");
                        return false;
                    default:
                        throw new ELException("TOOD");
                }
            }

            return super.visit(x);
        }
    }
}
