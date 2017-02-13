package net.damroo.imagefinderprototype.service.util;

public final class Utility {

    private Utility() {
    }

    public static int getLastPageNumber(double results, double resultsPerPage) {
        double page = results / resultsPerPage;
        return (int) Math.ceil(page);
    }
    
}
