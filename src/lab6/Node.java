package lab6;

public class Node {


    public Node next;
    public Node previous;
    public String value;
    public int index;

    public Node(final String value, final int index){

        this.index = index;
        this.value = value;
        this.next = null;
        this.previous = null;
    }

    public Node getNext(){
        return this.next;
    }

    public Node getPrevious(){
        return this.previous;
    }

    public void setNext(Node next) {
        this.next = next;
    }
    public void setPrevious(Node previous){
        this.previous = previous;
    }

    public boolean hasNext(){
        boolean next = false;

        if(this.next != null){
            next = true;
        }
        return next;
    }
}
