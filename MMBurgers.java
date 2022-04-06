
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
class AVLTree {

    public Node root;

    public class Node {
        public myCustomer customer;
        public int key;
        private int balance;
        public int height;
        public Node left, right, parent;

        Node(int id, Node p, myCustomer customerData) {
            key = id;
            parent = p;
            customer = customerData;
        }
    }

    public boolean insert(int key, myCustomer customer) {
        if (root == null) root = new Node(key, null, customer);
        else {
            Node n = root;
            Node parent;
            while (true) {
                if (n.key == key) return false;

                parent = n;

                boolean goLeft = n.key > key;
                n = goLeft ? n.left : n.right;

                if (n == null) {
                    if (goLeft) {
                        parent.left = new Node(key, parent, customer);
                    } else {
                        parent.right = new Node(key, parent, customer);
                    }
                    rebalance(parent);
                    break;
                }
            }
        }
        return true;
    }

    private void delete(Node node) {
        if (node.left == null && node.right == null) {
            if (node.parent == null) root = null;
            else {
                Node parent = node.parent;
                if (parent.left == node) {
                    parent.left = null;
                } else parent.right = null;
                rebalance(parent);
            }
            return;
        }
        if (node.left != null) {
            Node child = node.left;
            while (child.right != null) child = child.right;
            node.key = child.key;
            node.customer = child.customer;
            delete(child);
        } else {
            Node child = node.right;
            while (child.left != null) child = child.left;
            node.key = child.key;
            node.customer = child.customer;
            delete(child);
        }
    }

    public void delete(int delKey) {
        if (root == null) return;
        Node node = root;
        Node child = root;

        while (child != null) {
            node = child;
            child = delKey >= node.key ? node.right : node.left;
            if (delKey == node.key) {
                delete(node);
                return;
            }
        }
    }

    private void rebalance(Node n) {
        setBalance(n);

        if (n.balance == -2) {
            if (height(n.left.left) >= height(n.left.right)) n = rotateRight(n);
            else n = rotateLeftThenRight(n);

        } else if (n.balance == 2) {
            if (height(n.right.right) >= height(n.right.left)) n = rotateLeft(n);
            else n = rotateRightThenLeft(n);
        }

        if (n.parent != null) {
            rebalance(n.parent);
        } else {
            root = n;
        }
    }

    private Node rotateLeft(Node a) {

        Node b = a.right;
        b.parent = a.parent;

        a.right = b.left;

        if (a.right != null) a.right.parent = a;

        b.left = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        setBalance(a, b);

        return b;
    }

    private Node rotateRight(Node a) {

        Node b = a.left;
        b.parent = a.parent;

        a.left = b.right;

        if (a.left != null) a.left.parent = a;

        b.right = a;
        a.parent = b;

        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }

        setBalance(a, b);

        return b;
    }

    private Node rotateLeftThenRight(Node n) {
        n.left = rotateLeft(n.left);
        return rotateRight(n);
    }

    private Node rotateRightThenLeft(Node n) {
        n.right = rotateRight(n.right);
        return rotateLeft(n);
    }

    private int height(Node n) {
        if (n == null) return -1;
        return n.height;
    }

    private void setBalance(Node... nodes) {
        for (Node n : nodes) {
            reheight(n);
            n.balance = height(n.right) - height(n.left);
        }
    }

    public void printBalance() {
        printBalance(root);
    }

    private void printBalance(Node n) {
        if (n != null) {
            printBalance(n.left);
            System.out.printf("%s ", n.balance);
            printBalance(n.right);
        }
    }

    private void reheight(Node node) {
        if (node != null) {
            node.height = 1 + Math.max(height(node.left), height(node.right));
        }
    }

    public Node search(int key) {
        Node result = searchHelper(this.root, key);
        if (result != null) return result;
        else {
            return null;
        }


    }

    private Node searchHelper(Node root, int key) {
        // root is null or key is present at root
        if (root == null || root.key == key) return root;

        // key is greater than root's key
        if (root.key > key)
            return searchHelper(root.left, key); // call the function on the node's left child

        // key is less than root's key then
        // call the function on the node's right child as it is greater
        return searchHelper(root.right, key);
    }

}
class myCustomer{
    int id;
    int queueInd;
    int quantity;
    boolean inQueue,waitFood,leftBuild;
    int entryTime;
    int exitTime;
    int queueTime;
    int waitTime;
    int billingAt;
    int remainBurgers;

    myCustomer(int myID , int entryT, int k , int orderQuant){
        this.id =  myID;
        this.queueInd = k;
        this.entryTime = entryT;
        this.quantity = orderQuant;
        this.billingAt = this.entryTime + this.queueTime;
        remainBurgers = quantity;
        inQueue = true;
        leftBuild = false;
        waitFood = false;
        queueTime = 0;
        waitTime = exitTime - entryTime;
        exitTime = 0;

    }

}

class  myOrder{
    int customID;
    myCustomer customerVar;
    int quantity;
    int orderedAt;
    int remainingTime;
    int queueIndex;

    myOrder(myCustomer c, int q, int k, int t){
        this.customerVar = c;
        this.customID = c.id;
        this.quantity = q;
        this.queueIndex = k;
        this.orderedAt = t;
    }
}

//class myQueue{
//    Queue q;
//    int index;
//
//    myQueue(Queue p , int k){
//        this.q = p;
//        this.index =  k;
//    }
//}
class heapNode<E>{
    E data;
    int keyOne;
    int keyTwo;

    heapNode(E q, int keyOne , int keyTwo){
        this.data = q;
        this.keyOne = keyOne;
        this.keyTwo = keyTwo;
    }

}


class myHeap{

    private heapNode root;

    public  Vector<heapNode> myTree;
    public int heapSize;



    myHeap(){
        root = null;
        myTree = new Vector<>();
        myTree.add(null);
        //myTree.add(root);
        heapSize = myTree.size() - 1;
    }

    myHeap(heapNode rootnode){
        root = rootnode;
        myTree = new Vector<>();
        myTree.add(null);
        //myTree.add(root);
        heapSize =  myTree.size() -1 ;

    }

    public heapNode parentOf(int i){
        return myTree.elementAt(i/2);
    }

    public heapNode leftChildOf(int i){
        return myTree.elementAt(2*i);
    }
    public heapNode rightChildOf(int i){
        return myTree.elementAt((2*i) + 1);
    }

    public void bubbleUP(int i) {
        if (parentOf(i) == null) {
            return;
        }
        else {
            if (myTree.elementAt(i).keyOne == parentOf(i).keyOne) {
                if (myTree.elementAt(i).keyTwo == -1) {
                    return;
                }
                if (myTree.elementAt(i).keyTwo > parentOf(i).keyTwo) {
                    return;
                }
                else if (myTree.elementAt(i).keyTwo < parentOf(i).keyTwo) {
                    heapNode temp = parentOf(i);
                    myTree.set( i / 2,myTree.elementAt(i));
                    myTree.set(i, temp);
                    bubbleUP(i / 2);
                }
            }
            if (myTree.elementAt(i).keyOne > parentOf(i).keyOne) {
                return;
            } else if (myTree.elementAt(i).keyOne < parentOf(i).keyOne) {
                heapNode temp = parentOf(i);
                myTree.set( i / 2,myTree.elementAt(i));
                myTree.set(i, temp);
                bubbleUP(i / 2);
            }
        }
    }

    public void insert(heapNode e){
        myTree.add(e);
        bubbleUP(myTree.size() -1);
    }

    public void bubbleDown(int i){
        if (2*i > myTree.size() - 1){
            return;
        }
        else if (2*i == myTree.size() -1){
            int tempKeyOne = leftChildOf(i).keyOne;
            int tempKeyTwo = leftChildOf(i).keyTwo;
            if (myTree.elementAt(i).keyOne == tempKeyOne){
                if (myTree.elementAt(i).keyTwo < tempKeyTwo){
                    return;
                }
                else {
                    heapNode temp = leftChildOf(i);
                    myTree.set(2*i,myTree.elementAt(i));
                    myTree.set(i,temp);
                    bubbleDown(2*i);
                }
            }
            else if (myTree.elementAt(i).keyOne < tempKeyOne){
                return;
            }
            else {
                heapNode temp = leftChildOf(i);
                myTree.set(2*i,myTree.elementAt(i));
                myTree.set(i,temp);
                bubbleDown(2*i);
            }
        }
        else {
            int tempKeyOne =  Math.min(leftChildOf(i).keyOne, rightChildOf(i).keyOne);
            //System.out.println("tempKeyOne "+tempKeyOne);
            int tempKeyTwo =  Math.min(leftChildOf(i).keyTwo, rightChildOf(i).keyTwo);
            //System.out.println("tempKeyTwo "+tempKeyTwo);
            if (myTree.elementAt(i).keyOne == tempKeyOne) {
                if (myTree.elementAt(i).keyTwo == -1) {
                    return;
                }
                if (myTree.elementAt(i).keyTwo < tempKeyTwo) {
                    return;
                }
                else if (myTree.elementAt(i).keyTwo > tempKeyTwo) {
                    if (leftChildOf(i).keyTwo == tempKeyTwo){
                        heapNode temp = leftChildOf(i);
                        myTree.set(2*i,myTree.elementAt(i) );
                        myTree.set(i,temp );
                        bubbleDown(2*i);
                    }
                    if (rightChildOf(i).keyTwo == tempKeyTwo){
                        heapNode temp = rightChildOf(i);
                        myTree.set( (2*i) + 1,myTree.elementAt(i) );
                        myTree.set(i,temp );
                        bubbleDown((2*i) + 1);
                    }
                }
            }
            if (myTree.elementAt(i).keyOne < tempKeyOne) {
                return;
            }
            else if (myTree.elementAt(i).keyOne > tempKeyOne) {
                if (leftChildOf(i).keyOne == tempKeyOne){
                    heapNode temp = leftChildOf(i);
                    myTree.set(2*i,myTree.elementAt(i) );
                    myTree.set(i,temp );
                    bubbleDown(2*i);
                }
                if (rightChildOf(i).keyOne == tempKeyOne){
                    heapNode temp = rightChildOf(i);
                    myTree.set( (2*i) + 1,myTree.elementAt(i) );
                    myTree.set(i,temp );
                    bubbleDown((2*i) + 1);
                }
            }
        }
    }

    public void deleteMin(){
        heapNode temp = myTree.elementAt(myTree.size() - 1);
        myTree.set(1,temp);
        myTree.removeElementAt(myTree.size() -1);
        bubbleDown(1);
//        for (int i = 1; i < myTree.size(); i++) {
//            System.out.println("heap D "+myTree.get(i).data);
//        }

    }

    public heapNode findMinNode(){
        return myTree.get(1);
    }
}

class myHeapTwo{

    private heapNode root;

    public  Vector<heapNode> myTree;
    public int heapSize;



    myHeapTwo(){
        root = null;
        myTree = new Vector<>();
        myTree.add(null);
        //myTree.add(root);
        heapSize = myTree.size() - 1;
    }

    myHeapTwo(heapNode rootnode){
        root = rootnode;
        myTree = new Vector<>();
        myTree.add(null);
        //myTree.add(root);
        heapSize =  myTree.size() -1 ;

    }

    public heapNode parentOf(int i){
        return myTree.elementAt(i/2);
    }

    public heapNode leftChildOf(int i){
        return myTree.elementAt(2*i);
    }
    public heapNode rightChildOf(int i){
        return myTree.elementAt((2*i) + 1);
    }

    public void bubbleUP(int i) {
        if (parentOf(i) == null) {
            return;
        }
        else {
            if (myTree.elementAt(i).keyOne == parentOf(i).keyOne) {
                if (myTree.elementAt(i).keyTwo == -1) {
                    return;
                }
                if (myTree.elementAt(i).keyTwo < parentOf(i).keyTwo) {
                    return;
                }
                else if (myTree.elementAt(i).keyTwo > parentOf(i).keyTwo) {
                    heapNode temp = parentOf(i);
                    myTree.set( i / 2,myTree.elementAt(i));
                    myTree.set(i, temp);
                    bubbleUP(i / 2);
                }
            }
            if (myTree.elementAt(i).keyOne > parentOf(i).keyOne) {
                return;
            } else if (myTree.elementAt(i).keyOne < parentOf(i).keyOne) {
                heapNode temp = parentOf(i);
                myTree.set( i / 2,myTree.elementAt(i));
                myTree.set(i, temp);
                bubbleUP(i / 2);
            }
        }
    }

    public void insert(heapNode e){
        myTree.add(e);
        bubbleUP(myTree.size() -1);
    }

    public void bubbleDown(int i){
        if (2*i > myTree.size() - 1){
            return;
        }
        else if (2*i == myTree.size() -1){
            int tempKeyOne = leftChildOf(i).keyOne;
            int tempKeyTwo = leftChildOf(i).keyTwo;
            if (myTree.elementAt(i).keyOne == tempKeyOne){
                if (myTree.elementAt(i).keyTwo > tempKeyTwo){
                    return;
                }
                else {
                    heapNode temp = leftChildOf(i);
                    myTree.set(2*i,myTree.elementAt(i));
                    myTree.set(i,temp);
                    bubbleDown(2*i);
                }
            }
            else if (myTree.elementAt(i).keyOne < tempKeyOne){
                return;
            }
            else {
                heapNode temp = leftChildOf(i);
                myTree.set(2*i,myTree.elementAt(i));
                myTree.set(i,temp);
                bubbleDown(2*i);
            }
        }
        else {
            int tempKeyOne =  Math.min(leftChildOf(i).keyOne, rightChildOf(i).keyOne);
            //System.out.println("tempKeyOne "+tempKeyOne);
            int tempKeyTwo =  Math.min(leftChildOf(i).keyTwo, rightChildOf(i).keyTwo);
            //System.out.println("tempKeyTwo "+tempKeyTwo);
            if (myTree.elementAt(i).keyOne == tempKeyOne) {
                if (myTree.elementAt(i).keyTwo == -1) {
                    return;
                }
                if (myTree.elementAt(i).keyTwo > tempKeyTwo) {
                    return;
                }
                else if (myTree.elementAt(i).keyTwo < tempKeyTwo) {
                    if (leftChildOf(i).keyTwo == tempKeyTwo){
                        heapNode temp = leftChildOf(i);
                        myTree.set(2*i,myTree.elementAt(i) );
                        myTree.set(i,temp );
                        bubbleDown(2*i);
                    }
                    if (rightChildOf(i).keyTwo == tempKeyTwo){
                        heapNode temp = rightChildOf(i);
                        myTree.set( (2*i) + 1,myTree.elementAt(i) );
                        myTree.set(i,temp );
                        bubbleDown((2*i) + 1);
                    }
                }
            }
            if (myTree.elementAt(i).keyOne < tempKeyOne) {
                return;
            }
            else if (myTree.elementAt(i).keyOne > tempKeyOne) {
                if (leftChildOf(i).keyOne == tempKeyOne){
                    heapNode temp = leftChildOf(i);
                    myTree.set(2*i,myTree.elementAt(i) );
                    myTree.set(i,temp );
                    bubbleDown(2*i);
                }
                if (rightChildOf(i).keyOne == tempKeyOne){
                    heapNode temp = rightChildOf(i);
                    myTree.set( (2*i) + 1,myTree.elementAt(i) );
                    myTree.set(i,temp );
                    bubbleDown((2*i) + 1);
                }
            }
        }
    }

    public void deleteMin(){
        heapNode temp = myTree.elementAt(myTree.size() - 1);
        myTree.set(1,temp);
        myTree.removeElementAt(myTree.size() -1);
        bubbleDown(1);
//        for (int i = 1; i < myTree.size(); i++) {
//            System.out.println("heap D "+myTree.get(i).data);
//        }

    }

    public heapNode findMinNode(){
        return myTree.get(1);
    }
}
class myPattyClass{
    int keptOnGriddleAt;
    int toBeRemovedAt;
    int customID;

    myPattyClass(int id, int keptOn,int completeAt){
        this.customID = id;
        this.keptOnGriddleAt = keptOn;
        this.toBeRemovedAt = completeAt;
    }
}
class myGriddleClass{
    Vector<myPattyClass> vectorS;
    int headOfGriddle;
    int tailOfGriddle;
    //int inGriddle;
    int capacityVar;

    myGriddleClass(int m){
        vectorS = new Vector<myPattyClass>();
        capacityVar = m;
        headOfGriddle = 0;
        tailOfGriddle = 0;
        //inGriddle = this.tailOfGriddle - this.headOfGriddle - 1;
    }
}
//
//class myPattyClass{
//    int customID
//}

public class MMBurgers implements MMBurgersInterface {

    myHeap myCounters;
    AVLTree myCustomerSet = new AVLTree();

    myHeapTwo myOrdersHeap = new myHeapTwo();

    int numOfCustomers = 0;
    int totalTimeOfCust = 0;

    int GlobalTimeTracker = 0;

    myGriddleClass griddle;

    public boolean isEmpty(){
        int remainCustomInQueue = 0;
        int remainOrders = myOrdersHeap.myTree.size() - 1;
        int remainBurgers = griddle.tailOfGriddle;
        for (int i = 1; i < myCounters.myTree.size(); i++) {
            Queue thisQ = (Queue) myCounters.myTree.get(i).data;
            remainCustomInQueue += thisQ.size();

        }

        if (remainBurgers == 0 && remainOrders == 0 && remainCustomInQueue == 0 ){
            //System.out.println("true");
            return true;
        }
        else {
            //System.out.println("false");
            return false;
        }
    }

    public void setK(int k) throws IllegalNumberException {
        if (k <= 0) {
            throw new IllegalNumberException("illegal K");
        } else {

            myCounters = new myHeap();

            for (int i = 1; i <= k; i++) {
                Queue<myCustomer> q = new ArrayDeque<myCustomer>();
                //myQueue counterQ = new myQueue(q, i);
                heapNode<Queue<myCustomer>> counterQueue = new heapNode<Queue<myCustomer>>(q, q.size(), i);
                myCounters.insert(counterQueue);
            }

            //System.out.println("set K = " + (myCounters.myTree.size() - 1));
        }
    }

    public void setM(int m) throws IllegalNumberException {
        if (m <= 0) {
            throw new IllegalNumberException("illegal m");
        } else {
            griddle = new myGriddleClass(m);
            //System.out.println("set M = " + griddle.capacityVar);
        }
    }

    public void advanceTime(int t) throws IllegalNumberException{
        if(t < GlobalTimeTracker){
            throw new IllegalNumberException("Illegal time entered");
        }
        else{

            for (int i = GlobalTimeTracker ; i <= t ; i++) {

                //billing
                for (int j = 1; j < myCounters.myTree.size(); j++) {
                    heapNode thisNode = myCounters.myTree.get(j);
                    Queue thisQueue = (Queue) thisNode.data;
                    myCustomer frontCustomer = (myCustomer) thisQueue.peek();
                    if (frontCustomer != null) {
                        if (frontCustomer.billingAt <=  i) {
                            myOrder makeOrder = new myOrder(frontCustomer, frontCustomer.quantity, frontCustomer.queueInd, frontCustomer.billingAt);
                            heapNode nodeMakeOrder = new heapNode(makeOrder, frontCustomer.billingAt, frontCustomer.queueInd);

                            myOrdersHeap.insert(nodeMakeOrder);
                            thisQueue.poll();
                            thisNode.keyOne -= 1;
                            //myCustomerSet.search(frontCustomer.id).customer.waitFood = true;
                            //myCustomerSet.search(frontCustomer.id).customer.inQueue = false;
                            frontCustomer.waitFood = true;
                            frontCustomer.inQueue = false;

                        }
                    }


                }
                for (int j = 1; j < myCounters.myTree.size(); j++) {
                    myCounters.bubbleDown(j);
                    myCounters.bubbleUP(j);
                }

                //removePatty


                Vector<Integer> readyCustomers = new Vector<>();

                Vector<myPattyClass> readyPatties = new Vector<>();
                while (!griddle.vectorS.isEmpty() && griddle.vectorS.get(0).toBeRemovedAt <= i ) {
                        myCustomer currCustom = myCustomerSet.search(griddle.vectorS.get(0).customID).customer;
                        currCustom.remainBurgers -= 1;
                        if ( currCustom.remainBurgers == 0) {
                            readyCustomers.add(currCustom.id);
                            currCustom.exitTime = griddle.vectorS.get(0).toBeRemovedAt + 1;
                            //System.out.println("currCostom exit time"+ (currCustom.exitTime - currCustom.entryTime));
                            //myCustomerSet.search(thisCustomer.id).customer.leftBuild = true;
                            currCustom.waitFood = false;
                            currCustom.leftBuild = true;
                        }
                        //readyPatties.add(griddle.vectorS.get(0));

                        griddle.vectorS.remove(0);
                        griddle.tailOfGriddle = griddle.tailOfGriddle - 1;
//                        for (int j = 1; j < griddle.vectorS.size(); j++) {
//                            System.out.println("griddle " + j + "  "+ griddle.vectorS.get(j).customID );
//                        }

//                        System.out.println("----- tail of griddle" + griddle.tailOfGriddle);
//                        System.out.println("---- remain burgers of " + currCustom.id + " is " + currCustom.remainBurgers + "");



                    }




                //addPatty
                while (myOrdersHeap.myTree.size()  > 1  && griddle.tailOfGriddle < griddle.capacityVar ) {

                        heapNode nodeEarliestOrder = myOrdersHeap.findMinNode();
                        myOrder earliestOrder = (myOrder) nodeEarliestOrder.data;
                        myPattyClass addPatty = new myPattyClass(earliestOrder.customID, i, i + 10);

                        //System.out.println("earliest order quantity  == " + earliestOrder.quantity + "of id  ==  " + earliestOrder.customID);
                        if (earliestOrder.quantity <= griddle.capacityVar - griddle.tailOfGriddle ) {
                            for (int j = 1; j <= earliestOrder.quantity; j++) {
                                griddle.vectorS.add(addPatty);
                                griddle.tailOfGriddle += 1;
                                //System.out.println("tail of griddle part 1 "+ griddle.tailOfGriddle);
                            }
                            myOrdersHeap.deleteMin();
                        }
                        else if (earliestOrder.quantity > griddle.capacityVar - griddle.tailOfGriddle){
                            for (int j = 1; j <= griddle.capacityVar - griddle.tailOfGriddle; j++) {
                                griddle.vectorS.add(addPatty);
                                griddle.tailOfGriddle += 1;
                                earliestOrder.quantity = earliestOrder.quantity - 1;
                                //System.out.println("tail of griddle part 2 "+ griddle.tailOfGriddle);
                            }

                            //System.out.println("earliest order updated quant "+ earliestOrder.quantity);
                        }

                }
                //orderToCustomers

//                while (!readyPatties.isEmpty()){
//                    myCustomer currCustom = myCustomerSet.search(readyPatties.get(0).customID).customer;
//                    currCustom.remainBurgers -= 1;
//                    if (currCustom.remainBurgers == 0){
//                        currCustom.exitTime = readyPatties.get(0).toBeRemovedAt +1;
//                        currCustom.waitFood = false;
//                        currCustom.leftBuild = true;
//                    }
//                    readyPatties.remove(0);
//                }

//                for (int j = 0; j < readyCustomers.size(); j++) {
//                    myCustomer thisCustomer = myCustomerSet.search(readyCustomers.get(j)).customer;
//                    thisCustomer.exitTime = i +1;
//                    //System.out.println("customr exit time "+thisCustomer.exitTime);
//                    //myCustomerSet.search(thisCustomer.id).customer.waitFood = false;
//                    //myCustomerSet.search(thisCustomer.id).customer.leftBuild = true;
//                    thisCustomer.waitFood = false;
//                    thisCustomer.leftBuild = true;
//
//                }

            }
            GlobalTimeTracker = t;
            //System.out.println("advacne time happen  " + GlobalTimeTracker );

        }
    }

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException {
        if (t < GlobalTimeTracker || myCustomerSet.search(id) != null) {
            throw new IllegalNumberException("Illegal time is entered or Customer ID already there");
        } else {

            advanceTime(t);

            heapNode qNode = myCounters.findMinNode();
            myCustomer customVar = new myCustomer(id, t, qNode.keyTwo, numb);
            Queue queueMin = (Queue) qNode.data;
            myCustomer headOfQueue = (myCustomer) queueMin.peek();
            if (queueMin.isEmpty()) {
                customVar.queueTime = qNode.keyTwo;
            } else {
                customVar.queueTime = (headOfQueue.queueTime + headOfQueue.entryTime - GlobalTimeTracker) + (queueMin.size()) * qNode.keyTwo;
            }
            customVar.billingAt = customVar.entryTime + customVar.queueTime;
            myCustomerSet.insert(id, customVar);
            queueMin.add(customVar);
            qNode.keyOne = queueMin.size();
            myCounters.bubbleDown(1);
            //System.out.println("Customer " + id + " comes with an order of " + numb+" burgers." + "joins Queue " + customVar.queueInd + "billing time at" + customVar.billingAt);
            //System.out.println("arrive Customer happen  " + GlobalTimeTracker );
        }
    }

    public int customerState(int id, int t) throws IllegalNumberException{
        if (t < GlobalTimeTracker ){
            throw new IllegalNumberException("Illegal time or customer id");
        }
        else if (myCustomerSet.search(id) == null){
            return 0;
        }
        else {
            advanceTime(t);
            myCustomer customVar = myCustomerSet.search(id).customer;

            //System.out.println("Customer State of id " + id + " at time " + t);

            if (customVar == null){
                //System.out.println(-1);
                return -1;
            }
            else if (customVar.inQueue){
                //System.out.println("customer state "+customVar.queueInd);
                return customVar.queueInd;
            }
            else if (customVar.waitFood){
                //System.out.println("customer state "+myCounters.myTree.size());
                return myCounters.myTree.size();
            }
            else if (customVar.leftBuild){
                //System.out.println("customer state "+(myCounters.myTree.size() + 1));
                return myCounters.myTree.size() + 1;
            }


            else return -1;
        }
    }

    public int griddleState(int t) throws IllegalNumberException{
        if (t < GlobalTimeTracker){
            throw new IllegalNumberException("incorrect time");
        }
        else {
            advanceTime(t);
            //System.out.println("griddle State at time " + t);
            //System.out.println("griddle state "+griddle.tailOfGriddle );
            return griddle.tailOfGriddle ;
        }
    }

    public int griddleWait(int t) throws IllegalNumberException{
        if (t < GlobalTimeTracker){
            throw new IllegalNumberException("incorrect time");
        }
        else {
            advanceTime(t);
            int answer = 0;
            for (int i = 1; i < myOrdersHeap.myTree.size(); i++) {
                myOrder thisOrder = (myOrder) myOrdersHeap.myTree.get(i).data;
                answer += thisOrder.quantity ;
            }
            //System.out.println("griddle Wait at time " + t );
            //System.out.println(answer);
            return answer;
        }
    }

    public int customerWaitTime(int id) throws IllegalNumberException{
        if ( myCustomerSet.search(id) == null){
            throw new IllegalNumberException("illegal id");
        }
        else {
            myCustomer thisCustomer = myCustomerSet.search(id).customer;
            //System.out.println("Wait time of customer " + id + " is ");
            //System.out.println("customer wait time " +(thisCustomer.exitTime - thisCustomer.entryTime));
            return thisCustomer.exitTime - thisCustomer.entryTime;
        }
    }

    public float avgWaitTime(){
      float avgTime = 0;
      calcAvg(myCustomerSet.root);

      avgTime = ((float)totalTimeOfCust)/((float)numOfCustomers);
      //System.out.println("avg time - " + avgTime);
      return avgTime;

    }

    private void calcAvg(AVLTree.Node node){
        if (node == null){
            return;
        }
        else {
            numOfCustomers += 1;
            totalTimeOfCust += node.customer.exitTime - node.customer.entryTime;
            calcAvg(node.left);
            calcAvg(node.right);
        }
    }


}
