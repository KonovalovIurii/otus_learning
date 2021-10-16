package homework;

class Demo {
    public static void main(String[] args) {
        TestLoggingInterface myTestClass = Ioc.createmyTestClass();
        myTestClass.calculation(6, "строка ", "еще строка");
    }
}