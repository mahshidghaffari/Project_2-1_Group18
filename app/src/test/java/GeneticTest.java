import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import controller.Individual;

public class GeneticTest {
    /* Please write thorough tests for your contribution to the genetic algorithm.
    When we're gonna leave it running for hours to train it we need it to be smooth,
    so test everything seperatly
    */
    @Test public void testIndividualPiece(){
        Individual m = new Individual(true, false, false);
        assertEquals(true, m.getPieceWeights()[0] >= m.getOgPieceValues()[0] - m.getPieceRanges()[0] && m.getPieceWeights()[0] <= m.getOgPieceValues()[0] + m.getPieceRanges()[0]);
        assertEquals(true, m.getPieceWeights()[1] >= m.getOgPieceValues()[1] - m.getPieceRanges()[1] && m.getPieceWeights()[1] <= m.getOgPieceValues()[1] + m.getPieceRanges()[1]);
        assertEquals(true, m.getPieceWeights()[2] >= m.getOgPieceValues()[2] - m.getPieceRanges()[2] && m.getPieceWeights()[2] <= m.getOgPieceValues()[2] + m.getPieceRanges()[2]);
        assertEquals(true, m.getPieceWeights()[3] >= m.getOgPieceValues()[3] - m.getPieceRanges()[3] && m.getPieceWeights()[3] <= m.getOgPieceValues()[3] + m.getPieceRanges()[3]);
        assertEquals(true, m.getPieceWeights()[4] >= m.getOgPieceValues()[4] - m.getPieceRanges()[4] && m.getPieceWeights()[4] <= m.getOgPieceValues()[4] + m.getPieceRanges()[4]);
    }
    @Test public void testIndividualCC(){
        Individual m = new Individual(false,true,false);
        assertTrue(m.getCCWeights()[0] >= m.getOgCCWeights()[0] - m.getCCRanges()[0]);
        assertTrue(m.getCCWeights()[0] <= m.getOgCCWeights()[0] + m.getCCRanges()[0]);
        //assertTrue(m.getCCWeights()[1] >= m.getOgCCWeights()[1] - m.getCCRanges()[1]);
        assertTrue(m.getCCWeights()[1] <= m.getOgCCWeights()[1] + m.getCCRanges()[1]);
        assertTrue(m.getCCWeights()[2] >= m.getOgCCWeights()[2] - m.getCCRanges()[2]);
        assertTrue(m.getCCWeights()[2] <= m.getOgCCWeights()[2] + m.getCCRanges()[2]);
        assertTrue(m.getCCWeights()[3] >= m.getOgCCWeights()[3] - m.getCCRanges()[3]);
        assertTrue(m.getCCWeights()[3] <= m.getOgCCWeights()[3] + m.getCCRanges()[3]);
    }
    @Test public void testPieceMutation(){
        for(int i=0; i<1000; i++){
            Individual m = new Individual(true,false,false);
            Individual n = new Individual("");
            n.setPieceWeights(m.getPieceWeights());
            m.mutate(1.0);
            boolean flag = false;
            for(int j=0; j<5; j++){
                if(m.getPieceWeights()[j] != n.getPieceWeights()[j]){
                    flag = true;
                }
            }
            assertTrue(flag);
        }
    }
    @Test public void testCCMutation(){
        for(int i=0; i<1000; i++){
            Individual m = new Individual(false,true,false);
            Individual n = new Individual("");
            n.setPieceWeights(m.getPieceWeights());
            m.mutate(1.0);
            boolean flag = false;
            for(int j=0; j<5; j++){
                if(m.getCCWeights()[j] != n.getCCWeights()[j]){
                    flag = true;
                }
            }
            assertTrue(flag);
        }
    }
}
