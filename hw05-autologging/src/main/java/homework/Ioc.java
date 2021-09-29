package homework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createmyTestClass() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLogging());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface myTestClass;

        private final Method [] MethodList;

        DemoInvocationHandler(TestLoggingInterface myTestClass) {
            this.myTestClass = myTestClass;

            this.MethodList = Arrays.stream(TestLogging.class.getMethods())
                    .filter(method -> method.isAnnotationPresent(Log.class))
                    .toArray(Method[]::new);

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String argList = "";
            if (Arrays.stream(MethodList).map(c -> c.getName()).anyMatch(method.getName()::equals)) {
                for (Object arg : args) {
                    if (arg.getClass() == Object[].class) {
                        for (Object intArg : (Object[]) arg) {
                            argList = argList + " " + intArg;
                        }
                    } else {
                        argList = argList + " " + arg;
                    }
                }

            }
            System.out.println("executed method:" + method.getName() + ", param: " + argList);
            return method.invoke(myTestClass,args);
    }

    @Override
    public String toString() {
        return "DemoInvocationHandler{" +
                "myClass=" + myTestClass +
                '}';
    }
}
}
