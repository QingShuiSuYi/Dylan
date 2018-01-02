package com.weavernorth.enumTest;


/**
 * 枚举示例
 * @author Dylan
 *
 */
public enum Test01 {
	SPRING("春天"),SUMMER("夏天"),FALL("秋天"),WINTER("冬天");
    
    private final String name;
    
    private Test01(String name)
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }
	public static void main(String[] args) {
//		Test01[] values = Test01.values();
//		for (Test01 test01 : values) {
//			System.out.println(test01.getName());
//		}
		
		 
		 
	}
	 
	
}
