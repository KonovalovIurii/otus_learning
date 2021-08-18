package homework;

class simpleStringTest{
    private String str, str2, result;

    @Test
    public void doConcatination() {
        System.out.println("Do concatination");
        result = str.concat(str2);

    }

    @Test
    public int doError()  {
        System.out.println("Do error");
        return Integer.parseInt("error");
    }

    @After
    public String clear() {
        System.out.println("Do After");
        return result;
    }

    @Before
    public void getNewCalc(String s1, String s2){
        System.out.println("Do Before");
        str = s1;
        str2 = s2;
    }
}
