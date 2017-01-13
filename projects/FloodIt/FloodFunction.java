import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class FloodFunction {
    public Driver driver;
    public List<Coord> flooded_list = new LinkedList<>();
    
    public FloodFunction(final Driver _driver) {
        this.driver = _driver;
        this.flooded_list.add(new Coord(0, 0));
    }

    public boolean inbound(final Coord coord) {
        return coord.x > -1 && coord.x < this.driver.size && coord.y > -1 && coord.y < this.driver.size;
    }

    public Coord up(final Coord coord) {
        return new Coord(coord.x, coord.y-1);
    }

    public Coord down(final Coord coord) {
        return new Coord(coord.x, coord.y+1);
    }

    public Coord left(final Coord coord) {
        return new Coord(coord.x-1, coord.y);
    }

    public Coord right(final Coord coord) {
        return new Coord(coord.x+1, coord.y);
    }

    public void flood(final Map color_of_tiles, final Integer color) {
    	// students: put your code here!
    	//Group member: Helena Qin Shiyue Pei
    	int flood_size = flooded_list.size();
    	
    	for(int i = 0; i < flood_size; i++)
    	{
    		Coord curr = flooded_list.get(i);
    	
    		if(inbound(right(curr)) && (color == color_of_tiles.get(right(curr))))
    		{
    			if(!(flooded_list.contains(right(curr))))
    			{
    				
    				flooded_list.add(right(curr));
    			
    			}
    		}
    		if(inbound(left(curr)) && (color == color_of_tiles.get(left(curr))))
    		{
    			if(! (flooded_list.contains(left(curr))))
    			{
    				flooded_list.add(left(curr));
	
    			}
    		}
    		if(inbound(up(curr)) && color == color_of_tiles.get(up(curr)))
    		{
    			if(! (flooded_list.contains(up(curr))))
    			{
    				flooded_list.add(up(curr));
    				
    			}
    		}
    		if(inbound(down(curr)) && (color == color_of_tiles.get(down(curr))))
    		{
    			if(! (flooded_list.contains(down(curr))))
    			{
    				flooded_list.add(down(curr));
    			}
    		}
    	}
    	if (flood_size != flooded_list.size())
    		flood(color_of_tiles, color);

    }
    
    public void flood1(final Map color_of_tiles, final Integer color)
    {
    	Iterator<Map.Entry <Coord, Integer>> itor = color_of_tiles.entrySet().iterator();
    	
    	while(itor.hasNext())
    	{
    		Map.Entry<Coord, Integer> current = (Map.Entry)itor.next();
    		Coord coord = current.getKey();
    		Integer val = current.getValue();
    		
    		if(flood_list.contains(up(coord)) && color == val)
    		{
    			flooded_list.add(coord);
    		}
    		else if(flood_list.contains(down(coord)) && color == val)
    		{
    			flooded_list.add(coord);
    		}
    		else if(flood_list.contains(left(coord)) && color == val)
    		{
    			flooded_list.add(coord);
    		}
    		else if(flood_list.contains(right(coord)) && color == val)
    		{
    			flooded_list.add(coord);
    		}
    	}
    }

}
