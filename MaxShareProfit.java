public class MaxShareProfits {
    public static int maxProfits(int[] prices) {
        int minPrice = Integer.MAX_VALUE;  
        int maxProfits = 0;
        
        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;
            }
            int keuntungan = price - minPrice;
            if (keuntungan > maxProfits) {
                maxProfits = keuntungan;
            }
        }
        
        return maxProfits;
    }

    public static void main(String[] args) {
        
        int[] prices1 = {5, 1, 4, 3, 6, 2};
        System.out.println("Maximum income: " + maxProfits(prices1));  // Output: 5
        
        
        int[] prices2 = {8, 5, 3, 3, 0};
        System.out.println("Maximum income: " + maxProfits(prices2));  // Output: 0
    }
}