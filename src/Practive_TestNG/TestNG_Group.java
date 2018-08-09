package Practive_TestNG;

import org.testng.annotations.Test;

public class TestNG_Group {
  @Test (groups = "test01")
  public void f1() {
	  System.out.print("test group 01");
  }
  
  @Test(groups = "test03")
  public void f2() {
	  System.out.print("test group 03");
  }

  @Test(groups = "test02")
  public void f3() {
	  System.out.print("test group 02");
  }

}
