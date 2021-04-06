public class btreemap<Key extends Comparable<Key>, Value>{
    private static final int maxChildren = 4;
    private Node<Key, Value> root;
    private int height;
    private int size;

    public btreemap() {
		root = new Node<Key, Value>(0);
	}

    @SuppressWarnings("unchecked")
    private class Node<Key extends Comparable<Key>, Value>{
        private int NumberOfChildren;
        private Entry<Key, Value>[] childrenArray = new Entry[btreemap.maxChildren];

        public Node(int numberOfChildren){
            NumberOfChildren = numberOfChildren;
        }

        public Entry<Key, Value>[] getChildrenArray() {
            return childrenArray;
        }

        public int getNumberOfChildren() {
            return NumberOfChildren;
        }

        public void setNumberOfChildren(int childrenNumber) {
            this.NumberOfChildren = childrenNumber;
        }
        
        public String toString() {
            String result = "{ ";
            for (int i = 0; i < NumberOfChildren; i++) {
                Entry<Key, Value> entry = childrenArray[i];
                result = result + entry.toString() + ", ";
            }
            result = result + " }";
            return result;
        }
    }

    private class Entry<Key extends Comparable<Key>, Value>{
        private Key key;
        private Value value;
        private Node<Key, Value> next;

        public Entry(Key key, Value value, Node<Key, Value> next){
            this.key = key;
            this.value = value;
            this.next = next;
        }
        public Key getKey(){
            return key;
        }
        public Value getValue(){
            return value;
        }
        public void setKey(Key key){
            this.key = key;
        }
        public void setValue(Value value){
            this.value = value;
        }
        public Node<Key, Value> getNext(){
            return next;
        }
        public void setNext(Node<Key, Value> next){
            this.next = next;
        }
        public String toString() {
            return "(Key: " + key + " Value: " + value + ")";
        }
    }

    public int getSize() {
		return size;
	}

    public int getHeight() {
		return height;
	}

    public boolean isEmpty() { return root == null; }

    public void deleteTree(){
        root = null;
        System.out.println("Tree deleted: " + this.isEmpty());
    }

    public boolean isContains(Key key){
        return search(root, key, height) != null;
    } 

    public Value get(Key key) {
		return search(root, key, height);
	}
    private Value search(Node<Key, Value> node, Key key, int treeHeight) {
		Entry<Key, Value>[] children = node.getChildrenArray();

		if (treeHeight == 0) {
			for (int j = 0; j < node.getNumberOfChildren(); j++) {
				if (equal(key, children[j].getKey())) {
					return (Value) children[j].getValue();
				}
			}
		} else {
			for (int j = 0; j < node.getNumberOfChildren(); j++) {
				if (node.getNumberOfChildren() == j + 1 || less(key, children[j + 1].getKey()))
					return search(children[j].getNext(), key, treeHeight - 1);
			}
		}
		return null;
	}
    private boolean less(Key k1, Key k2) {
		return k1.compareTo(k2) < 0;
	}
    private boolean equal(Key k1, Key k2) {
		return k1.compareTo(k2) == 0;
	}

    public void put(Key key, Value value) {
		Node<Key, Value> nodeFromSplit = insert(root, key, value, height);
		size++;
		if (nodeFromSplit == null) {
			return;
		}

		Node<Key, Value> newRoot = new Node<Key, Value>(2);
		newRoot.getChildrenArray()[0] = new Entry<Key, Value>(root.getChildrenArray()[0].getKey(), null, root);
		newRoot.getChildrenArray()[1] = new Entry<Key, Value>(nodeFromSplit.getChildrenArray()[0].getKey(), null, nodeFromSplit);
		root = newRoot;
        split(root);
		height++;
	}

    private Node<Key, Value> insert(Node<Key, Value> node, Key key,Value value, int treeHeight) {
        int newEntryPosition;
        Entry<Key, Value> nodeToInsert = new Entry<Key, Value>(key, value, null);

        if (treeHeight == 0) {
            for (newEntryPosition = 0; newEntryPosition < node.getNumberOfChildren(); newEntryPosition++) {
                if (less(key, node.getChildrenArray()[newEntryPosition].getKey())) {
                    break;
                }
            }
        } else {
            for (newEntryPosition = 0; newEntryPosition < node.getNumberOfChildren(); newEntryPosition++) {
                if ((node.getNumberOfChildren() == newEntryPosition + 1)|| less(key, node.getChildrenArray()[newEntryPosition + 1].getKey())) {
                    Node<Key, Value> nodeFromSplit = insert(node.getChildrenArray()[newEntryPosition++].getNext(), key, value, treeHeight - 1);
                    if (nodeFromSplit == null) {
                        return null;
                    }
                    nodeToInsert.setKey(nodeFromSplit.getChildrenArray()[0].getKey());
                    nodeToInsert.setNext(nodeFromSplit);
                    break;
                }
            }
        }

        for (int i = node.getNumberOfChildren(); i > newEntryPosition; i--) {
            node.getChildrenArray()[i] = node.getChildrenArray()[i - 1];

        }

        node.getChildrenArray()[newEntryPosition] = nodeToInsert;
        node.setNumberOfChildren(node.getNumberOfChildren() + 1);
        if (node.getNumberOfChildren() < maxChildren) {
            return null;

        } else {
            return split(node);
            }
    }

    private Node<Key, Value> split(Node<Key, Value> nodeToSplit) {
		Node<Key, Value> newNode = new Node<Key, Value>(maxChildren / 2);
		nodeToSplit.setNumberOfChildren(maxChildren / 2);
		for (int j = 0; j < maxChildren / 2; j++) {
			newNode.getChildrenArray()[j] = nodeToSplit.getChildrenArray()[maxChildren / 2 + j];
		}
		return newNode;
	}

    public String toString() {
		return toString(root, height, "") + "\n";
	}

	private String toString(Node<Key, Value> currentNode, int ht, String indent) {
		String outputString = "";
		Entry<Key, Value>[] childrenArray = currentNode.getChildrenArray();

		if (ht == 0) {
			for (int j = 0; j < currentNode.getNumberOfChildren(); j++) {
				outputString += indent + childrenArray[j].getKey() + " " + childrenArray[j].getValue() + "\n";
			}
		} else {
			for (int j = 0; j < currentNode.getNumberOfChildren(); j++) {
				if (j > 0) {
					outputString += indent + "(" + childrenArray[j].getKey() + ")\n";
				}
				outputString += toString(childrenArray[j].getNext(), ht - 1, indent + "     ");
			}
		}
		return outputString;
	}
}