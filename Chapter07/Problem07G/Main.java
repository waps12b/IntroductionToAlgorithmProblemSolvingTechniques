import java.io.*;
import java.lang.*;
import java.util.*;

class Solution {

	/**
	 * @brief   getNumberOfInversions 함수의 축약형
	 *
	 */
	public static long getNumberOfInversions(int[] arr) {
		int N = arr.length;
		int[] buffer = new int[N];
		return getNumberOfInversions(arr, 0, N - 1, buffer);
	}

	/**
	 * @brief       mergeSort를 수행하며 내부적으로 inversion pair의 수를 계산하는 함수
	 *              arr[left] ~ arr[right]를 merge sort로 정렬한다
	 *
	 * @param arr
	 * @param left
	 * @param right
	 * @param buffer
	 * @return      arr[left] ~ arr[right] 범위에 존재하는 inversion pair의 수를 반환한다
	 */
	public static long getNumberOfInversions(int[] arr, int left, int right, int[] buffer) {
		if (left >= right) {
			// 원소가 하나 이하라면 pair가 존재할 수 없다
			return 0;
		}

		// 두 범위를 나눌 중간지점
		int mid = (left + right) / 2;

		// 좌 우 영역에 대해서만 존재하는 inversion pair를 계산한다
		long leftCount = getNumberOfInversions(arr, left, mid, buffer);
		long rightCount = getNumberOfInversions(arr, mid + 1, right, buffer);

		long globalCount = leftCount + rightCount;

		// 두 영역에 걸치는 inversion pair의 수를 계산한다
		int i = left;
		int j = mid + 1;
		int k = left;
		while (i <= mid && j <= right) {
			if (arr[i] <= arr[j]) {
				buffer[k++] = arr[i++];
			} else {

				// 오른쪽 배열에 존재하던 원소가 버퍼에 추가된다
				// <==> 아직 남아있는 왼쪽 배열의 원소들은 현재 arr[rj]보다 크다
				buffer[k++] = arr[j++];

				// 남은 큰 원소 <=> inversion pair
				int leftRemain = (mid - i + 1);
				globalCount += leftRemain;
			}
		}

		while (i <= mid) {
			buffer[k++] = arr[i++];
		}

		while (j <= right) {
			buffer[k++] = arr[j++];
		}

		for (int index = left; index <= right; index += 1) {
			arr[index] = buffer[index];
		}

		return globalCount;
	}

}

public class Main {
	public static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		int N = scanner.nextInt();

		int[] arr = new int[N];
		for (int i = 0; i < N; i += 1) {
			arr[i] = scanner.nextInt();
		}

		long answer = Solution.getNumberOfInversions(arr);

		System.out.println(answer);
	}

}
