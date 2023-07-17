package team7.example.ToyProject3.util;


public class ErrorScript {

    public static String alertBuild(String message) {
        StringBuilder sb = new StringBuilder();
        sb.append("<script>");
        sb.append(String.format("alert('%s');", message));
        sb.append("history.back();");
        sb.append("</script>");
        return sb.toString();
    }
}
