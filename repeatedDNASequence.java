/*
Problem:
*/

// Approach 1
// TC: O(n * dnaLength)
// SC: O(n * dnaLength)
class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        int dnaLength = 10;
        
        if (s == null || s.length() <= dnaLength) {
            return new ArrayList<>();
        }
        
        HashSet<String> seen = new HashSet<>();
        HashSet<String> resultSet = new HashSet<>();
        
        for (int start = 0; start < s.length() - dnaLength + 1; ++start) {
        	// Takes a considerable amount of time if dna length varying or large.
            String substring = s.substring(start, start + dnaLength);
            if (seen.contains(substring)) {
                resultSet.add(substring);
            } else {
                seen.add(substring);
            }
        }
        
        return new ArrayList<>(resultSet);
    }
}

// Approach 2: To avoid using substring
// TC: O(n)
// SC: O(n)
class Solution {
    public List<String> findRepeatedDnaSequences(String s) {
        int dnaLength = 10;
        
        if (s == null || s.length() <= dnaLength) {
            return new ArrayList<>();
        }
        
        // 4 characters so 2 bits to represent each character
        HashMap<Character, Integer> charNumMap = new HashMap<>();
        charNumMap.put('A', 0);
        charNumMap.put('C', 1);
        charNumMap.put('G', 2);
        charNumMap.put('T', 3);
        
        int num[] = new int[s.length()];
        for (int i = 0; i < s.length(); ++i) {
            num[i] = charNumMap.get(s.charAt(i));
        }
        
        // Used to store the encoded substring at every window
        int bitmask = 0;
        HashSet<Integer> seen = new HashSet<>();
        HashSet<String> resultSet = new HashSet<>();
        
        for (int start = 0; start < s.length() - dnaLength + 1; ++start) {
            if (start == 0) {
                for (int i = 0; i < dnaLength; ++i) {
                    bitmask = bitmask << 2;
                    bitmask = bitmask | num[i];
                }
            } else {
                bitmask <<= 2;
                bitmask = bitmask | num[start + dnaLength - 1];
                // We need to clear out the left most 2 bits now to move the sliding window right
                // DNA length = 10 and each character takes 2 bits so left shift 2 * dnalength
                bitmask = bitmask & ~(3 << 2 * dnaLength);
            }
            
            // This part can be optimized by maintaining a hash map instead of repeatedly creating substrings for duplicates
            if (seen.contains(bitmask)) {
                resultSet.add(s.substring(start, start + dnaLength));
            }
            seen.add(bitmask);
        }
        
        return new ArrayList<>(resultSet);
    }
}