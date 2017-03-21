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

	int cheapRoute(int r, int c) {
		Integer[] loc = new Integer[max_row];
		if (c == 0) {
			return costs[r][c];
		}
		else {
			loc[0] = costs[r][c] + cheapRoute(r, c-1);
			loc[1] = costs[r][c] + travel[r][(r+1)%3] + cheapRoute((r+1)%3, c-1);
			loc[2] = costs[r][c] + travel[r][(r+2)%3] + cheapRoute((r+2)%3, c-1);
		}

		int min = Integer.MAX_VALUE;
		for (int x: loc) {
			if (x < min) min = x;
		}
		return min;
	}

	int dynPro() {
		ArrayList<ArrayList<Node>> table = new ArrayList<ArrayList<Node>>(3);
		Integer[] loc = new Integer[max_row];

		for (int row = 0; row < 3; row++) {
			table.add(new ArrayList<Node>(12));
		}
		for (int i = 0; i < max_row; i++) {
			for (int j = 0; j < max_col; j++) {
				table.get(i).add(new Node());
			}
		}

		table.get(0).get(0).cost = costs[0][0];
		table.get(1).get(0).cost = costs[1][0];
		table.get(2).get(0).cost = costs[2][0];

		for (int i = 1; i < max_col; i++) {
			for (int j = 0; j < max_row; j++) {
				
				for (int k = 0; k < max_row; k++) {
					loc[k] = table.get(k).get(i-1).cost + costs[j][i] + travel[k][j];
				}
				
				table.get(j).get(i).cost = loc[0];

				for (int k = 1; k < loc.length; k++) {
					if (loc[k] < table.get(j).get(i).cost) {
						table.get(j).get(i).cost = loc[k];
					}
				}
			}
		}

		int l = Integer.MAX_VALUE;
		for (int k = 0; k < max_row; k++) {
			if (table.get(k).get(max_col-1).cost < l) {
				l = table.get(k).get(max_col-1).cost;
			}
		}

		return l;
	}
	
	public static void main(String[] args) {
		Main m = new Main();
		int l = Integer.MAX_VALUE;
		for (int i = 0; i < m.max_row; i++) {
			int temp = m.cheapRoute(i, m.max_col - 1);
			//System.out.println(temp);
			if (temp < l) l = temp;
		}
		System.out.println(l);

		System.out.println(m.dynPro());
		



	}
}
