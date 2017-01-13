package routingWiresOnAChip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;


public class Routing {

    public static ArrayList<ArrayList<Coord>>
 findPaths(Board board, ArrayList<Coord[]> points)
    {
    	//Board new_board = board.deepCopy();
    	ArrayList<ArrayList<Coord>> result = new ArrayList<ArrayList<Coord>>();
    	for(int j = 0; j < points.size(); j++) {
    		ArrayList<Coord> path = new ArrayList<Coord>();
    		Map<Coord, Boolean> visited = new HashMap<Coord, Boolean>();
    		Map<Coord, Coord> parent = bfs(board, points.get(j)[0], visited);
    		Coord curr = points.get(j)[1];
    		int val = board.getValue(curr);
    		while(curr != null) {
    			path.add(curr);
    			board.putValue(curr, val);
    			curr = parent.get(curr);
    		}
    		Collections.reverse(path);
    		if(path.size() > 0 && path.get(0) == points.get(j)[0] && path.get(path.size()-1) == points.get(j)[1])
    			result.add(path);
    	}
    	//System.out.println(result.size());
    	if(result.size() != points.size()) {
    		//change the board to the original one:    		
    		for(Entry<Coord, Integer> entry: board.grid.entrySet()) {
    			if(board.getValue(entry.getKey()) > 0) {
    				int val = entry.getValue();
    				if(!entry.getKey().equals(points.get(val-1)[0]) && !entry.getKey().equals(points.get(val-1)[1])) {
    					board.putValue(entry.getKey(), 0);
    				}
    			}
    		}
    		//System.out.println("after modify:");
    		//board.pretty_print_grid();
    		//System.out.println();
    		result.clear();
    		Map<ArrayList<Coord>, Integer> result_map = new HashMap<ArrayList<Coord>, Integer>();
    		ArrayList<Coord[]> sorted_points = calcDistance(points);
    		for(int i = 0; i < sorted_points.size(); i++) {
    			ArrayList<Coord> path = new ArrayList<Coord>();
    			Map<Coord, Boolean> visited = new HashMap<Coord, Boolean>();
    			Map<Coord, Coord> parent2 = bfs(board, sorted_points.get(i)[0], visited);
    			//print parent map
    			/*for(Entry<Coord, Coord> entry: parent2.entrySet()) {
    				System.out.println("child is: " + entry.getKey().toString());
    				System.out.println("parent is: " + entry.getValue().toString());
    			}*/
    			Coord curr = sorted_points.get(i)[1];
    			int val = board.getValue(curr);
    			//System.out.println("val is: " + val);
    			while(curr != null) {
    				path.add(curr);
    				board.putValue(curr, val);
    				curr = parent2.get(curr);
    				//System.out.println("added!");
    			}
    			Collections.reverse(path);
    			result_map.put(path, val);
    			/*for(Coord c: path) {
    				System.out.println(c.toString());
    			}*/
    		}
    		Map<ArrayList<Coord>, Integer> sorted_result_map = sortMapByValue2(result_map);
    		for(ArrayList<Coord> x: sorted_result_map.keySet()) {
    			result.add(x);
    		}
    	}
    	return result;
    }
    
    public static <V> Map<Coord, Coord> bfs(Board board, Coord start, Map<Coord, Boolean> visited) {
    	Map<Coord, Coord> parent = new HashMap<Coord, Coord>();
    	Queue<Coord> Q = new LinkedList<Coord>();
    	Q.add(start);
    	visited.put(start, true);
    	while (Q.size() != 0) {
    		Coord c = Q.poll();
    		for (Coord coord: board.adj(c)) {
    			if(! board.isObstacle(coord)) {
    				if(board.getValue(coord) ==  board.getValue(start) ||!board.isOccupied(coord)) {
    					if (!visited.containsKey(coord)) {
    						parent.put(coord, c);
    						visited.put(coord, true);
    						Q.add(coord);
    					}
    				}
    			}
    		}
    	}
    	return parent;
    }
    
    //calculate the distance of each pair, and sort points in ascending order by their distances
    public static ArrayList<Coord[]> calcDistance(ArrayList<Coord[]> points) {
    	HashMap<Coord[], Double> map = new HashMap<Coord[], Double>();
    	for(Coord[] pair: points) {
    		double dis = Math.sqrt(Math.pow((pair[1].x - pair[0].x), 2) + Math.pow((pair[1].y - pair[0].y), 2));
    		map.put(pair, dis);
    	}
    	Map<Coord[], Double> sorted = sortMapByValue(map);
    	ArrayList<Coord[]> result = new ArrayList<Coord[]>();
    	for(Coord[] pair: sorted.keySet()) {
    		result.add(pair);
    	}
		return result; 	
    }
    
    //sort a map by value in ascending order
    public static Map<Coord[], Double> sortMapByValue(Map<Coord[], Double> unsorted) {
    	//convert map to list of map
    	List<Map.Entry<Coord[], Double>> list = new LinkedList<Map.Entry<Coord[], Double>>(unsorted.entrySet());
    	//sort list, switch position for o1 and o2
    	Collections.sort(list, new Comparator<Map.Entry<Coord[], Double>>() {
    		public int compare(Map.Entry<Coord[], Double> o1,
    				Map.Entry<Coord[], Double> o2) {
    			return (o1.getValue()).compareTo(o2.getValue());
    		}
    	});
    	//loop through the sorted list and put it into a new map
    	Map<Coord[], Double> sorted = new LinkedHashMap<Coord[], Double>();
    	for(Map.Entry<Coord[], Double> entry: list) {
    		sorted.put(entry.getKey(), entry.getValue());
    	}
    	return sorted;
    }
    
    public static Map<ArrayList<Coord>, Integer> sortMapByValue2(Map<ArrayList<Coord>, Integer> unsorted) {
    	//convert map to list of map
    	List<Entry<ArrayList<Coord>, Integer>> list = new LinkedList<Map.Entry<ArrayList<Coord>, Integer>>(unsorted.entrySet());
    	//sort list, switch position for o1 and o2
    	Collections.sort(list, new Comparator<Map.Entry<ArrayList<Coord>, Integer>>() {
    		public int compare(Map.Entry<ArrayList<Coord>, Integer> o1,
    				Map.Entry<ArrayList<Coord>, Integer> o2) {
    			return (o1.getValue()).compareTo(o2.getValue());
    		}
    	});
    	//loop through the sorted list and put it into a new map
    	Map<ArrayList<Coord>, Integer> sorted = new LinkedHashMap<ArrayList<Coord>, Integer>();
    	for(Map.Entry<ArrayList<Coord>, Integer> entry: list) {
    		sorted.put(entry.getKey(), entry.getValue());
    	}
    	return sorted;
    }
    
    public static void main(String[] args) {
    	Map<Coord[], Double> map = new HashMap<Coord[], Double>();
    	Coord[] pair1 = new Coord[2];
    	pair1[0] = new Coord(2, 2);
    	pair1[1] = new Coord(2, 2);
    	Coord[] pair2 = new Coord[2];
    	pair2[0] = new Coord(1, 1);
    	pair2[1] = new Coord(1, 1);
    	Coord[] pair3 = new Coord[2];
    	pair3[0] = new Coord(3, 3);
    	pair3[1] = new Coord(3, 3);
    	Coord[] pair4 = new Coord[2];
    	pair4[0] = new Coord(0, 0);
    	pair4[1] = new Coord(0, 0);
    	map.put(pair1, 2.0);
    	map.put(pair2, 1.0);
    	map.put(pair3, 3.0);
    	map.put(pair4, 0.1);
    	Map<Coord[], Double> sorted = sortMapByValue(map);
    	ArrayList<Coord[]> result = new ArrayList<Coord[]>();
    	for(Coord[] pair: sorted.keySet()) {
    		result.add(pair);
    	}
    	/*for(double d: sorted.values()) {
    		System.out.println(d);
    	}*/
    	for(Coord[] c: result) {
    		System.out.println(c[0].toString() + " " + c[1].toString());
    	}
    }
}
