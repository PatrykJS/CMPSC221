package smith.patryk;

/**
 *
 * @author Patryk Smith
 */
public class Main {
    
    public static void main(String[] args){
        double[] resistorValues = {
            10.0,	10.2,	10.5,	10.7,	11.0,	11.3,	11.5,	11.8,	12.1,	12.4,	12.7,	13.0,
            13.3,	13.7,	14.0,	14.3,	14.7,	15.0,	15.4,	15.8,	16.2,	16.5,	16.9,	17.4,
            17.8,	18.2,	18.7,	19.1,	19.6,	20.0,	20.5,	21.0,	21.5,	22.1,	22.6,	23.2,
            23.7,	24.3,	24.9,	25.5,	26.1,	26.7,	27.4,	28.0,	28.7,	29.4,	30.1,	30.9,
            31.6,	32.4,	33.2,	34.0,	34.8,	35.7,	36.5,	37.4,	38.3,	39.2,	40.2,	41.2,
            42.2,	43.2,	44.2,	45.3,	46.4,	47.5,	48.7,	49.9,	51.1,	52.3,	53.6,	54.9,
            56.2,	57.6,	59.0,	60.4,	61.9,	63.4,	64.9,	66.5,	68.1,	69.8,	71.5,	73.2,
            75.0,	76.8,	78.7,	80.6,	82.5,	84.5,	86.6,	88.7,	90.9,	93.1,	95.3,	97.6
        };
        final double DF = 1.848;
        final double RATIO = 0.152;
        
        double r1 = 0.0;
        double r2 = 0.0;
        int solutions = 0;
        for(int i = 0; i < resistorValues.length; i++){
            for(int j = 0; j < resistorValues.length; j++){
                r1 = resistorValues[i] * 100.0;
                r2 = resistorValues[j] * 100.0; 
                
                if(Math.abs((r1/r2) - RATIO) < 0.2){
                    System.out.printf("R1: %.2f  Ohms, R2: %.2f Ohms\n", r1, r2);
                    solutions++;
                }
            }
        }
        for(int i = 0; i < resistorValues.length; i++){
            for(int j = 0; j < resistorValues.length; j++){
                r1 = resistorValues[i] * 1000.0;
                r2 = resistorValues[j] * 1000.0; 
                
                if(Math.abs((r1/r2) - RATIO) < 0.2){
                    System.out.printf("R1: %.2f  Ohms, R2: %.2f Ohms\n", r1, r2);
                    solutions++;
                }
            }
            
        }
        for(int i = 0; i < resistorValues.length; i++){
            for(int j = 0; j < resistorValues.length; j++){
                r1 = resistorValues[i] * 10000.0;
                r2 = resistorValues[j] * 10000.0; 
                
                if(Math.abs((r1/r2) - RATIO) < 0.2){
                    System.out.printf("R1: %.2f  Ohms, R2: %.2f Ohms\n", r1, r2);
                    solutions++;
                }
            }
        }
        for(int i = 0; i < resistorValues.length; i++){
            for(int j = 0; j < resistorValues.length; j++){
                r1 = resistorValues[i] * 100000.0;
                r2 = resistorValues[j] * 100000.0; 
                
                if(Math.abs((r1/r2) - RATIO) < 0.2){
                    System.out.printf("R1: %.2f  Ohms, R2: %.2f Ohms\n", r1, r2);
                    solutions++;
                }
            }
        }
        System.out.println(solutions + " solutions found!");
    }
}
