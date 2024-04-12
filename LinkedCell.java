public class LinkedCell {

    public long value;
    public long low;
    public long high;
    public LinkedCell prev;
    public LinkedCell next;

    public LinkedCell(long l, long h, long v, LinkedCell p, LinkedCell n) {
        low = l;
        high = h;
        value = v;
        prev = p;
        next = n;
    }

}
