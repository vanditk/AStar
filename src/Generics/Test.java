/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Generics;

/**
 *
 * @author vandit
 */
public class Test {
    
    public static double calculateHeuristic(double long1,double lat1,double long2,double lat2)
    {//columbus,39.99,82.99
        //pittsburgh,40.40,79.84
        //austin,30.30,97.75
        //double lat1 = 40.40,lat2=30.30;
        //double long1 = 79.84,long2=97.75;
        double value;
        value = Math.sqrt(((69.5*(lat1-lat2)*69.5*(lat1-lat2))) + (69.5*Math.cos((lat1+lat2)/360 * Math.PI)*(long1 - long2)*(long1 - long2)));
        System.out.println("pitts to austin :"+value);
        return value;
    }
 
    public static void main(String args[])
    {
        //System.out.println)("");
        //calculateHeuristic();
 
    
    }
    
}
