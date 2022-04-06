public class mainClass {
    public static void main(String[] args) {

        heapNode nodeOne =  new heapNode("a", 1, 3);
        heapNode nodeTwo = new heapNode("b", 2, 1);
        heapNode nodeThree =  new heapNode("c", 3, 2);
        heapNode nodeFour =  new heapNode("d", 4, 6);
        heapNode nodeFive = new heapNode("e", 4, 4);
        heapNode nodeSix =  new heapNode("f", 6, 5);

        myHeapTwo thisHeap = new myHeapTwo();

        thisHeap.insert(nodeFive);
        System.out.println("----"+thisHeap.findMinNode().data);
        thisHeap.insert(nodeSix);
        System.out.println("----"+thisHeap.findMinNode().data);

        thisHeap.insert(nodeOne);
        System.out.println("----"+thisHeap.findMinNode().data);

        thisHeap.insert(nodeThree);
        System.out.println("----"+thisHeap.findMinNode().data);

        thisHeap.insert(nodeTwo);
        System.out.println("----"+thisHeap.findMinNode().data);

        thisHeap.insert(nodeFour);
        System.out.println("----"+thisHeap.findMinNode().data);


        System.out.println(thisHeap.findMinNode().data);
        thisHeap.deleteMin();
        System.out.println(thisHeap.findMinNode().data);
        thisHeap.deleteMin();
        System.out.println(thisHeap.findMinNode().data);
        thisHeap.deleteMin();
        System.out.println(thisHeap.findMinNode().data);
        thisHeap.deleteMin();
        System.out.println(thisHeap.findMinNode().data);
        thisHeap.deleteMin();
        System.out.println(thisHeap.findMinNode().data);

    }
}
