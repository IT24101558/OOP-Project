package DSA;

import java.util.List;

public class Main {

    // Insertion Sort for List<Member> by renewalDue date (String comparison)
    public static void insertionSortMembers(List<Member> members) {
        if (members == null || members.size() <= 1) {
            return; // Already sorted or nothing to sort
        }

        for (int i = 1; i < members.size(); i++) {
            Member key = members.get(i);
            int j = i - 1;

            // Compare renewalDue dates as strings.
            // For more robust date comparison, consider parsing to Date objects.
            while (j >= 0 && members.get(j).getRenewalDue().compareTo(key.getRenewalDue()) > 0) {
                members.set(j + 1, members.get(j));
                j = j - 1;
            }
            members.set(j + 1, key);
        }
    }

    public static void main(String[] args) {
        String filePath = "backend/data/members.json"; // Relative path from project root

        // 1. Read members from file
        List<Member> members = FileUtil.readMembersFromFile(filePath);
        System.out.println("Original members: " + members);

        // 2. Use CustomQueue for Membership Renewal Requests
        CustomQueue<String> renewalRequestQueue = new CustomQueue<>();

        // Simulate adding some member IDs to the queue for renewal processing
        if (!members.isEmpty()) {
            // Ensure members list is not empty before accessing elements
            renewalRequestQueue.enqueue(members.get(0).getId()); // Use enqueue
            if (members.size() > 1) {
                renewalRequestQueue.enqueue(members.get(1).getId()); // Use enqueue
            }
        }
        System.out.println("\nRenewal Request Queue: " + renewalRequestQueue.toString()); // Use toString()

        // Process requests from queue (conceptual sketch)
        // Using .dequeue() instead of .poll() and .isEmpty()
        while (!renewalRequestQueue.isEmpty()) {
            String memberIdToProcess = renewalRequestQueue.dequeue(); // Use dequeue
            System.out.println("Processing renewal for member ID: " + memberIdToProcess);
            // In a real system, you would:
            // - Find the member by ID
            // - Perform renewal logic (e.g., update renewal date, send notification)
            // - Potentially update the member in the list and write back to file
        }

        // 3. Sort members by renewalDue using Insertion Sort
        insertionSortMembers(members);
        System.out.println("\nSorted members by renewalDue (initial load): " + members);

        // 4. Example of adding a new member, re-sorting, and saving
        Member newMember = new Member("M004", "David Lee", "2023-11-15");
        System.out.println("\nAdding new member: " + newMember);
        members.add(newMember); // Add the new member to the list
        System.out.println("Members after adding new member (before re-sort): " + members);

        insertionSortMembers(members); // Re-sort the list with the new member
        System.out.println("Members after adding and re-sorting: " + members);

        FileUtil.writeMembersToFile(filePath, members); // Save changes
        System.out.println("All members, including new and sorted, written to " + filePath);
    }
}

