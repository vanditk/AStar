/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package searchusa;

import Generics.AStarPath;
import java.util.Comparator;

/**
 *
 * @author vandit
 */
class DynamicComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        AStarPath path1 = (AStarPath) o1;
        AStarPath path2 = (AStarPath) o2;
        if (path1.getPathLength() < path2.getPathLength()) {
            return -1;
        } else if (path1.getPathLength() > path2.getPathLength()) {
            return 1;

        } else {
            return 0;
        }

    }
}
