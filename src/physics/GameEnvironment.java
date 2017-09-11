package physics;

/**
 * @author Oriel Gaash
 * @version 1.7
 * @since 2015-04-17
 */

import java.util.ArrayList;
import java.util.List;

import objects.Collidable;

/**
 * this class makes the game environment which we will be playing. defines the
 * list of objects, and gets the width of the game from the Game class.
 */
public class GameEnvironment {

    private List objects;

    /**
     * constructor.
     */
    public GameEnvironment() {

        this.objects = new ArrayList();

    }

    /**
     * add the given collidable to the environment.
     * @param c the collidable object.
     */
    public void addCollidable(Collidable c) {
        this.objects.add(c);

    }

    /**
     * @param c the collidable we want to remove.
     * @return the collidable object we want to remove.
     */
    public boolean removeCollidable(Collidable c) {
        return this.objects.remove(c);
    }

    /**
     * Assume an object moving from line.start() to line.end(). If this object
     * will not collide with any of the collidables in this collection, return
     * null. Else, return the information about the closest collision that is
     * going to occur.
     * @param trajectory line of the ball.
     * @return the info of the collision object.
     */

    public CollisionInfo getClosestCollision(Line trajectory) {

        List collidingWithTrajectory = new ArrayList();
        List collidingObjectIndex = new ArrayList();

        List obj = new ArrayList(this.objects);

        for (int i = 0; i < obj.size(); i++) {
            Collidable c = (Collidable) obj.get(i);
            Point p = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (p != null) {
                Integer num = i;
                collidingWithTrajectory.add(p);
                collidingObjectIndex.add(num);

            }
        }

        // no collision in this trajectory.

        if (collidingWithTrajectory.isEmpty()) {
            return null;
        }
        Point closest = (Point) collidingWithTrajectory.get(0);
        int closestIndex = ((Integer) collidingObjectIndex.get(0)).intValue();
        double a = 58;
        for (int i = 0; i < collidingWithTrajectory.size(); i++) {

            double d1 = trajectory.start().distance(closest);
            double d2 = trajectory.start().distance((Point) collidingWithTrajectory.get(i));
            if (d2 < d1) {
                closest = (Point) collidingWithTrajectory.get(i);
                closestIndex = ((Integer) collidingObjectIndex.get(i)).intValue();
            }
        }
        Collidable c = (Collidable) this.objects.get(closestIndex);

        return new CollisionInfo(c, closest);

    }
}