package demo.bean.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public class Aop {  
    
    public void begin(){  
        System.out.println("��ʼ����/�쳣");  
    }  
      
    public void after(){  
        System.out.println("�ύ����/�ر�");  
    }  
      
    public void afterReturning() {  
        System.out.println("afterReturning()");  
    }  
      
    public void afterThrowing(){  
        System.out.println("afterThrowing()");  
    }  
      
    public void around(ProceedingJoinPoint pjp) throws Throwable{  
        System.out.println("����ǰ....");  
        pjp.proceed();  // ִ��Ŀ�귽��  
        System.out.println("���ƺ�....");  
    }  
}
