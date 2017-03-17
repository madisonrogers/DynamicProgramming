import java.util.ArrayList;
import java.util.Arrays;

public class Main {
	
	
	public int[][] costs;
	public int[][] travel;
	public String[] cities;
	private int max_row;
	private int max_col;
	
	public ArrayList<String> routes = new ArrayList<String>();
	public ArrayList<Integer> totals = new ArrayList<Integer>();


	public Main() {
		// Row 1 for NY, 2 for LA, 3 for DEN
		costs = new int[][] {
			{8,3,10,43,15,48,5,40,20,30,28,24},
			{18,1,35,18,10,19,18,10,8,5,8,20},
			{40,5,8,13,21,12,4,27,25,10,5,15}
		};
		max_row = 3;
		max_col = 12;
		cities = new String[] {"NY", "LA", "DEN"};
		travel = new int[][] {
			{0,20,15},
			{20,0,10},
			{15,10,0}
		};
	}
	
	int cheapRoute(int sum, int r, int c, String s) {
		Integer[] loc = new Integer[max_row];
		s = s + cities[r] + " ";
		if (c == max_col - 1) {
			int t = sum + costs[r][c];
			totals.add(t);
			routes.add(s);
			return t;
		}
		else {
			loc[0] = cheapRoute(sum + costs[r][c], r, c+1, s);
			loc[1] = cheapRoute(sum + costs[r][c] + travel[r][(r+1)%3], (r+1)%3, c+1, s);
			loc[2] = cheapRoute(sum + costs[r][c] + travel[r][(r+2)%3], (r+2)%3, c+1, s);
		}
		
		int min = Integer.MAX_VALUE;
		for (int x: loc) {
			if (x < min) min = x;
		}
		return min;
	}
	
	int dynPro() {
		return 0;
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		int l = Integer.MAX_VALUE;
		for (int i = 0; i < m.max_row; i++) {
			int temp = m.cheapRoute(0, i, 0, "");
			if (temp < l) l = temp;
		}
		System.out.println(l);
		System.out.println(m.routes.get(m.totals.indexOf(l)));
		
	}
}
