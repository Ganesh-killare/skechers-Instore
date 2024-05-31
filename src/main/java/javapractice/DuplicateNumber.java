package javapractice;

public class DuplicateNumber {

	// Find Duplicate Number in Array
static int count= 0 ;
	public static void main(String[] args) {
		System.out.println("This is the Duplicates in the Array");

		int numbers[] = { 1, 1, 2, 3, 4, 4, 5, 6, 7, 7, 8, 9, 9 };
		
		for (int i = 0; i <= numbers.length - 1; i++) {
			for (int j = i + 1; j < numbers.length; j++) {
				if (numbers[i] == numbers[j]) {
					System.out.println(numbers[i]);
					count++;
					break;
				}

			}
		}
System.out.println("In this array duplicates count is "+count);
	}
}
