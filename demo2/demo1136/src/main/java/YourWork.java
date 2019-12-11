import java.util.HashMap;

public class YourWork {
    /**
     * 将一个任意对象转换为JSON格式的字符串并返回
     * 请使用原生代码实现
     * @param obj 任意类型的对象
     * @return JSON类型的字符串
     */

    public String objectToJson(Object obj){
        //todo 在此处填写你的代码
        if(obj instanceof HashMap){
            HashMap map = (HashMap)obj;
            StringBuilder json = new StringBuilder();
            json.append("{");
            if(map.size() > 0){
                int i = 1;
                for(Object key : map.keySet()){
                    json.append("\"p"+i+"\"");
                    json.append(objectToJson(key));
                    json.append(":");
                    json.append(objectToJson(map.get(key)));
                    json.append(",");
                    json.setCharAt(json.length() - 1, '}');
                    i++;
                }
            }else{
                return "\"\"";
            }

        }else{
            return obj.toString();
        }
    }
    public void junit() {
        Person p1=new Person("张杰",25,"重庆");
        Person p2=new Person("杨逍",56,"光明顶");
        HashMap<String,Person> map=new HashMap<String, Person>();
        map.put("p1",p1);map.put("p2",p2);
        String Str_map1=objectToJson(map);
        System.out.println(assertTrue("{\"address\":\"重庆\",\"age\":25,\"name\":\"张杰\"}".equals(objectToJson(p1))));
        System.out.println(assertTrue("{\"address\":\"光明顶\",\"age\":56,\"name\":\"杨逍\"}".equals(objectToJson(p2))));
        System.out.println(assertTrue("{\"p1\":{\"address\":\"重庆\",\"age\":25,\"name\":\"张杰\"},\"p2\":{\"address\":\"光明顶\",\"age\":56,\"name\":\"杨逍\"}}".equals(Str_map1)));
    }

    public static boolean assertTrue(boolean flag) {
        StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
        StackTraceElement e = stacktrace[2];
        String methodName = e.getMethodName();
        String className = e.getClassName();
        int line = e.getLineNumber();
        String index = "类:" + className + "方法:" + methodName + "行:" + line + "";
        if (!flag) {
            System.out.println("测试失败!" + index);
            System.exit(-1);
        }
        return true;
    }
    //主类
    public static void main(String[] args) {
        new YourWork().junit();
    }
}