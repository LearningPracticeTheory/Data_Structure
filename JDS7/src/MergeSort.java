
public class MergeSort {

	/* 
	 * divide until single node, then merge from deep to shallow,
	 * which means the length of arrays increases with the merge
	 * and the arrays are sorted when merge
	 */
	public static<AnyType extends Comparable<AnyType>> void sort0(AnyType array[]) {
		if(array.length <= 1) {
			return;
		}
		/*
		 * ERROR on use `array = divide(array)`; which only change the formal parameter
		 * technically only change formal parameter points to the new array[], 
		 * but actual parameter still points to original array[],
		 * which means it didn't change the actual parameter at all
		 */
		AnyType tmpArray[] = divide(array);
		for(int i = 0; i < tmpArray.length; i++) { //so change the elements of original array one by one
			array[i] = tmpArray[i]; //which are in heap
		}
	}
	
	/*
	 * divide the array that we get
	 */
	@SuppressWarnings("unchecked")
	private static<AnyType extends Comparable<AnyType>>
	AnyType[] divide(AnyType array[]) { 
		
		/*
		 * odd divide -> evenArray + single element -> merge back into array
		 */
		if(array.length % 2 != 0 && array.length != 1) { //length >= 3
			AnyType tmpArray[] = (AnyType[]) new Comparable[array.length-1];
			for(int i = 0; i < tmpArray.length; i++) {
				tmpArray[i] = array[i];
			}
			tmpArray = divide(tmpArray);
			return merge(tmpArray, array[array.length-1]);
		}

		AnyType array1[] = (AnyType[]) new Comparable[array.length/2];
		AnyType array2[] = (AnyType[]) new Comparable[array.length/2];
		/*
		 * equally divide array into array1 & array2
		 */
		for(int i = 0; i < array1.length; i++) {
			array1[i] = array[i];
			array2[i] = array[i+array1.length];
		}
		
		if(array1.length != 1) {
			array1 = divide(array1);
			array2 = divide(array2);
		}
		
		return merge(array1, array2);
	}

	/*
	 * merge array1 & array2 into array
	 * case include evenMerge & oddMerge
	 */
	@SuppressWarnings("unchecked")
	private static<AnyType extends Comparable<AnyType>>
	AnyType[] merge(AnyType array1[], AnyType array2[]) {
		AnyType array[] = (AnyType[]) new Comparable[array1.length + array2.length];
		int index1 = 0;
		int index2 = 0;
		int index; //array.index
		for(index = 0; index < array.length; index++) {
			if(index1 == array1.length || index2 == array2.length) {
				break;
			}
			if(array1[index1].compareTo(array2[index2]) < 0) {
				array[index] = array1[index1++];
			} else {
				array[index] = array2[index2++];
			}
		}
		if(index1 == array1.length) {
			for(; index2 < array2.length; index2++) {
				array[index++] = array2[index2];
			}
		} else {
			for(; index1 < array1.length; index1++) {
				array[index++] = array1[index1];
			}
		}
		return array;
	}
	
	/*
	 * merge a element & an array
	 * of course treat theElement as an single node array
	 */
	@SuppressWarnings("unchecked")
	private static<AnyType extends Comparable<AnyType>>
	AnyType[] merge(AnyType[] tmpArray, AnyType theElement) { //odd only
		AnyType array[] = (AnyType[]) new Comparable[1];
		array[0] = theElement;
		return merge(array, tmpArray);
	}
	
/*-------------------------------------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	public static<AnyType extends Comparable<AnyType>> void sort1(AnyType array[]) {
		AnyType tmpArray[] = (AnyType[]) new Comparable[array.length];
		divide(array, tmpArray, 0, array.length); //[start, end);
	}
	
	private static<AnyType extends Comparable<AnyType>> 
	void divide(AnyType array[], AnyType tmpArray[], int leftStart, int rightEnd) {
		/*
		 * binary divide + post-order merge
		 * @see BinarySearch in algorithm
		 */
		if(leftStart < rightEnd-1) {
			int center = (leftStart + rightEnd) / 2; //== leftStart + (rightEnd - leftStart) / 2;
			divide(array, tmpArray, leftStart, center);
			divide(array, tmpArray, center, rightEnd);
			merge(array, tmpArray, leftStart, center, rightEnd);
		}
	}

	private static<AnyType extends Comparable<AnyType>> 
	void merge(AnyType[] array, AnyType[] tmpArray, int leftStart, int rightStart, int rightEnd) {
		int leftEnd = rightStart;
		int index = leftStart;
		int numOfElements = rightEnd-leftStart;

		while(leftStart < leftEnd && rightStart < rightEnd) {
			if(array[leftStart].compareTo(array[rightStart]) < 0) {
				tmpArray[index++] = array[leftStart++];
			} else {
				tmpArray[index++] = array[rightStart++];
			}
		}
		
		while(leftStart < leftEnd) {
			tmpArray[index++] = array[leftStart++];
		}
		
		while(rightStart < rightEnd) {
			tmpArray[index++] = array[rightStart++];
		}
		
		for(int i = 0; i < numOfElements; i++) {
			array[--rightEnd] = tmpArray[rightEnd];
		}
		
	}

/*-------------------------------------------------------------------------------------*/
	
	public static<AnyType extends Comparable<AnyType>> void sort2(AnyType array[]) {
		if(array.length <= 1) {
			return;
		}
		AnyType tmpArray[] = divide2(array);
		for(int i = 0; i < tmpArray.length; i++) { 
			array[i] = tmpArray[i];
		}
	}
	
	@SuppressWarnings("unchecked")
	private static<AnyType extends Comparable<AnyType>>
	AnyType[] divide2(AnyType array[]) { 
		int center = array.length/2;
		AnyType array1[] = (AnyType[]) new Comparable[center];
		AnyType array2[] = (AnyType[]) new Comparable[array.length-center];
		
		for(int i = 0; i < center; i++) {
			array1[i] = array[i];
		}
		int index = 0;
		for(int i = center; i < array.length; i++) {
			array2[index++] = array[i];
		}
		
		if(array.length > 2) {
			array1 = divide2(array1);
			array2 = divide2(array2);
		}
		
		return merge2(array1, array2);
	}
	
	@SuppressWarnings("unchecked")
	private static<AnyType extends Comparable<AnyType>>
	AnyType[] merge2(AnyType array1[], AnyType array2[]) {
		AnyType array[] = (AnyType[]) new Comparable[array1.length + array2.length];
		int index1 = 0;
		int index2 = 0;
		int index = 0;
		
		while(index1 < array1.length && index2 < array2.length) {
			if(array1[index1].compareTo(array2[index2]) < 0) {
				array[index++] = array1[index1++];
			} else {
				array[index++] = array2[index2++];
			}
		}
		
		while(index1 < array1.length) {
			array[index++] = array1[index1++];
		}
		
		while(index2 < array2.length) {
			array[index++] = array2[index2++];
		}
		
		return array;
	}
	
}
