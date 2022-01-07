import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import controller.Individual;

public class GeneticTest {
    /* Please write thorough tests for your contribution to the genetic algorithm.
    When we're gonna leave it running for hours to train it we need it to be smooth,
    so test everything seperatly
    */
    @Test public void testIndividual(){
        Individual m = new Individual();
        assertEquals(true, m.getWeights()[0] >= 5.0 && m.getWeights()[0] <= 15.0);
        assertEquals(true, m.getWeights()[1] >= 20.0 && m.getWeights()[1] <= 40.0);
        assertEquals(true, m.getWeights()[2] >= 20.0 && m.getWeights()[2] <= 40.0);
        assertEquals(true, m.getWeights()[3] >= 35.0 && m.getWeights()[3] <= 65.0);
        assertEquals(true, m.getWeights()[4] >= 60.0 && m.getWeights()[4] <= 120.0);
    }
}
