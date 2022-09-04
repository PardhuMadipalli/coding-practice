package problems.miscellaneous;

import java.util.Stack;

public class SimplifyPath {
    public String simplifyPath(String path) {
        String[] portions = path.split("/");
        Stack<String> stack = new Stack<>();
        for(String po: portions) {
//            System.out.println("pot:" + po+"EOF");
            if(!po.isEmpty() && !po.equals(".")){
                if(po.equals("..")) {
                    if(!stack.isEmpty())
                        stack.pop();
                } else {
                    stack.push(po);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        while(!stack.isEmpty()){
            sb.insert(0, "/" + stack.pop());
        }
        if(sb.length() == 0){
            sb.append("/");
        }
        return new String(sb);
    }

    public static void main(String[] args) {
        System.out.println(new SimplifyPath().simplifyPath("/abc///def/..///"));
    }
}
