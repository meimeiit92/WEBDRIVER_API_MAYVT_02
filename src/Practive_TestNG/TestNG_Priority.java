package Practive_TestNG;

import org.testng.annotations.Test;

public class TestNG_Priority {
  @Test (priority=1, enabled = true)
  public void f() {
	  System.out.println("test priority =1, enable = true");
  }
  
  @Test (priority=4, enabled = true)
  public void f2() {
	  System.out.println("test priority =4, enable = true");
  }
  
  @Test (priority=3, enabled = true)
  public void f3() {
	  System.out.println("test priority =3, enable = true");
  }
  
  @Test (priority=2, enabled = false)
  public void f4() {
	  System.out.println("test priority =2, enable = true");
  }
}
