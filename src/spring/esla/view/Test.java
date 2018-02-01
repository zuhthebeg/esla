package spring.esla.view;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int max[]  = {1, 2, 1, 1};
		 int maxCount = 0;
		
		 int maxValue = max[0];
		for(int k=1;k<4; k++){
			if(maxValue <= max[k]){
				maxValue = max[k];
				maxCount = k;
			}
		}
		
		System.out.println("test : " + maxValue + " / order : " + maxCount);
	}

}
