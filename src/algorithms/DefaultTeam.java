package algorithms;

import java.awt.Point;
import java.util.*;

public class DefaultTeam {

    private final double epsilon = 0.1; // Paramètre d'approximation (1 + ε)
    private final int neighborhoodBound = (int) Math.ceil(1 / (epsilon * epsilon) * Math.log(1 / epsilon));

    public ArrayList<Point> calculDominatingSet(ArrayList<Point> points, int edgeThreshold) {
        Set<Point> dominatingSet = new HashSet<>();

        // Étape 1 : Découpage du graphe en neighborhoods restreints
        List<Set<Point>> neighborhoods = constructBoundedNeighborhoods(points, edgeThreshold);

        // Étape 2 : Approximation locale stricte pour chaque neighborhood
        for (Set<Point> neighborhood : neighborhoods) {
            dominatingSet.addAll(approximateLocalDominatingSet(neighborhood, edgeThreshold));
        }

        // Étape 3 : Optimisation globale par élagage
        dominatingSet = optimizeDominatingSet(dominatingSet, points, edgeThreshold);

        System.out.println("Taille finale de l'ensemble dominant : " + dominatingSet.size());
        return new ArrayList<>(dominatingSet);
    }

    /**
     * Étape 1 : Construction des neighborhoods avec borne sur leur taille.
     */
    private List<Set<Point>> constructBoundedNeighborhoods(ArrayList<Point> points, int edgeThreshold) {
        List<Set<Point>> neighborhoods = new ArrayList<>();
        Set<Point> visited = new HashSet<>();

        for (Point v : points) {
            if (!visited.contains(v)) {
                Set<Point> neighborhood = new HashSet<>();
                Queue<Point> queue = new LinkedList<>();
                queue.add(v);
                int level = 0;

                while (!queue.isEmpty() && level < neighborhoodBound) {
                    int size = queue.size();
                    while (size-- > 0) {
                        Point current = queue.poll();
                        if (visited.add(current)) {
                            neighborhood.add(current);
                            for (Point neighbor : points) {
                                if (!visited.contains(neighbor) && current.distance(neighbor) <= edgeThreshold) {
                                    queue.add(neighbor);
                                }
                            }
                        }
                    }
                    level++;
                }
                neighborhoods.add(neighborhood);
            }
        }
        return neighborhoods;
    }

    /**
     * Étape 2 : Approximation locale stricte pour un neighborhood donné.
     */
    private Set<Point> approximateLocalDominatingSet(Set<Point> subset, int edgeThreshold) {
        Set<Point> localDominatingSet = new HashSet<>();
        Set<Point> uncovered = new HashSet<>(subset);

        while (!uncovered.isEmpty()) {
            Point bestPoint = null;
            int maxCoverage = -1;

            // Trouve le point avec le maximum de couverture
            for (Point candidate : subset) {
                int coverage = 0;
                for (Point p : uncovered) {
                    if (candidate.distance(p) <= edgeThreshold) {
                        coverage++;
                    }
                }
                if (coverage > maxCoverage) {
                    maxCoverage = coverage;
                    bestPoint = candidate;
                }
            }

            if (bestPoint != null) {
                final Point chosenPoint = bestPoint; // Rend la variable effectively final
                localDominatingSet.add(chosenPoint);

                // Utilise une variable temporaire pour la lambda
                uncovered.removeIf(p -> chosenPoint.distance(p) <= edgeThreshold);
            }
        }
        return localDominatingSet;
    }


    /**
     * Étape 3 : Optimisation globale par élagage.
     */
    private Set<Point> optimizeDominatingSet(Set<Point> dominatingSet, ArrayList<Point> points, int edgeThreshold) {
        Set<Point> optimizedSet = new HashSet<>(dominatingSet);

        for (Point p : new ArrayList<>(optimizedSet)) {
            optimizedSet.remove(p);
            if (!isValidDominatingSet(optimizedSet, points, edgeThreshold)) {
                optimizedSet.add(p);
            }
        }
        return optimizedSet;
    }

    /**
     * Vérifie si l'ensemble domine tous les points.
     */
    private boolean isValidDominatingSet(Set<Point> dominatingSet, ArrayList<Point> points, int edgeThreshold) {
        for (Point target : points) {
            boolean covered = false;
            for (Point domPoint : dominatingSet) {
                if (target.distance(domPoint) <= edgeThreshold) {
                    covered = true;
                    break;
                }
            }
            if (!covered) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        ArrayList<Point> points = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            points.add(new Point(random.nextInt(1000), random.nextInt(1000)));
        }
        int edgeThreshold = 30;

        DefaultTeam team = new DefaultTeam();
        ArrayList<Point> result = team.calculDominatingSet(points, edgeThreshold);
        System.out.println("Ensemble dominant final : " + result.size());
    }
}
