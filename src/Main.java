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

		table.get(0).get(0).location = 0;
		table.get(1).get(0).location = 1;
		table.get(2).get(0).location = 2;

		for (int i = 1; i < max_col; i++) {
			for (int j = 0; j < max_row; j++) {
				if (table.get(j).get(i-1).location == 0) {
					loc[0] = table.get(j).get(i-1).cost + costs[0][i];
					loc[1] = table.get(j).get(i-1).cost + costs[1][i] + 20;
					loc[2] = table.get(j).get(i-1).cost + costs[2][i] + 15;
				}
				
				if (table.get(j).get(i-1).location == 1) {
					loc[0] = table.get(j).get(i-1).cost + costs[0][i] + 20;
					loc[1] = table.get(j).get(i-1).cost + costs[1][i];
					loc[2] = table.get(j).get(i-1).cost + costs[2][i] + 10;
				}
				
				if (table.get(j).get(i-1).location == 2) {
					loc[0] = table.get(j).get(i-1).cost + costs[0][i] + 15;
					loc[1] = table.get(j).get(i-1).cost + costs[1][i] + 10;
					loc[2] = table.get(j).get(i-1).cost + costs[2][i];
				}
				
				
				table.get(j).get(i).cost = loc[0];
				table.get(j).get(i).location = 0;

				for (int k = 1; k < loc.length; k++) {
					if (loc[k] < table.get(j).get(i).cost) {
						table.get(j).get(i).cost = loc[k];
						table.get(j).get(i).location = k;
					}
				}
			}
		}
		String tem;
		for (int i = 0; i < max_row; i++) {
			tem = "";
			for (int j = 0; j < max_col; j++) {
				tem = tem + table.get(i).get(j).cost + " ";
			}
			System.out.println(tem);
		}

		int l = Integer.MAX_VALUE;
		for (int k = 0; k < max_row; k++) {
			if (table.get(k).get(max_col-1).cost < l) {
				l = table.get(k).get(max_col-1).cost;
			}
		}

		return l;
	}
	
	int dynPro2() {
		ArrayList<ArrayList<Node>> table = new ArrayList<ArrayList<Node>>(12);
		Integer[] loc = new Integer[max_row];

		for (int row = 0; row < 12; row++) {
			table.add(new ArrayList<Node>(3^row * 3));
		}
		for (int i = 0; i < max_row; i++) {
			for (int j = 0; j < max_col; j++) {
				table.get(i).add(new Node());
			}
		}
		
		table.get(0).get(0).cost = costs[0][0];
		table.get(0).get(1).cost = costs[1][0];
		table.get(0).get(2).cost = costs[2][0];
		
		table.get(0).get(0).location = 0;
		table.get(0).get(1).location = 1;
		table.get(0).get(2).location = 2;
		
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < table.get(i).size(); j++) {
				
			}
		}
		
		return 0;
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
		//System.out.println(m.routes.get(m.totals.indexOf(l)));

		System.out.println(m.dynPro());
		
		System.out.println(m.dynPro2());



	}
}
