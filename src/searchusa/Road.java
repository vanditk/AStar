/**
 *
 * @author vandit NCSU Student ID : 001085913
 */
package searchusa;

public class Road implements Comparable<Road> {

    private String[] cities = new String[2];
    private int distance;

    public Road(String src, String dest, int length) {
        cities[0] = src;
        cities[1] = dest;
        distance = length;

    }
    @Override
    public String toString()
    {
        return "Road: "+cities[0]+" -- "+cities[1];
    }

    @Override
    public int compareTo(Road anotherRoad) {
        return this.getDistance() < anotherRoad.getDistance() ? -1 : this.getDistance() > anotherRoad.getDistance() ? 1 : 0;
    }
    /**
     * @return the distance
     */
    public int getDistance() {
        return distance;
    }

    public boolean containsCity(String cityName) {
        if (cities[0].equalsIgnoreCase(cityName) || cities[1].equalsIgnoreCase(cityName)) {
            return true;
        } else {
            return false;
        }
    }
    public String getConnectingCity(String city) throws Exception
    {
        if(city.equalsIgnoreCase(cities[0]))
        {
            return cities[1];
        }
        else if(city.equalsIgnoreCase(cities[1]))
        {
            return cities[0];
        }
        else 
        {
            throw new Exception("origin city not found");
        }
    }
           
}
