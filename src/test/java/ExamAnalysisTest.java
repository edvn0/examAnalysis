import com.bth.analysis.ExamAnalysis;
import org.junit.Assert;
import org.junit.Test;

public class ExamAnalysisTest {

  private final double[] VALUES = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

  @Test
  public void testMean() {
    ExamAnalysis analysis = new ExamAnalysis();

    double mean = analysis.generateMean(VALUES, VALUES.length);

    Assert.assertEquals(mean, 5.5, Math.pow(10, -5));
  }

  @Test
  public void testMedianOdd() {
    ExamAnalysis analysis = new ExamAnalysis();

    double[] oddValues = {1, 2, 3, 4, 5, 6, 7, 8, 9};
    double median = analysis.generateMedian(oddValues);

    Assert.assertEquals(median, 5d, Math.pow(10, -5));
  }

  @Test
  public void testMedianEven() {
    ExamAnalysis analysis = new ExamAnalysis();

    double median = analysis.generateMedian(VALUES);

    Assert.assertEquals(median, (5d + 6d) / 2, Math.pow(10, -3));
  }

  @Test
  public void testStandardDeviation() {
    ExamAnalysis analysis = new ExamAnalysis();

    double stddev = analysis
        .generateStandardDeviation(analysis.generateMean(VALUES, VALUES.length), VALUES.length,
            VALUES);

    Assert.assertEquals(stddev, 2.8722813d, Math.pow(10, -5));
  }

  @Test
  public void testVariance() {
    ExamAnalysis analysis = new ExamAnalysis();

    double variance = analysis
        .generateVariance(VALUES, analysis.generateMean(VALUES, VALUES.length), VALUES.length);

    Assert.assertEquals(variance, 8.250000d, Math.pow(10, -5));
  }


}