package com.weavernorth.enumTest;
/**
 * 根据枚举值调用方法
 * @author Dylan
 *
 */
public enum Operation {
	PLUS {
		@Override
		public double eval(double x, double y) {
			// TODO Auto-generated method stub
			return 0;
		}
	},MINUS {
		@Override
		public double eval(double x, double y) {
			// TODO Auto-generated method stub
			return 0;
		}
	},TIMES {
		@Override
		public double eval(double x, double y) {
			// TODO Auto-generated method stub
			return 0;
		}
	},DIVIDE {
		@Override
		public double eval(double x, double y) {
			// TODO Auto-generated method stub
			return 0;
		}
	};
    
    public abstract double eval(double x, double y);
    
    
}

