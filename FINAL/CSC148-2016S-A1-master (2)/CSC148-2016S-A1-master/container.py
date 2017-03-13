class Container:
    """A container that holds objects.

    This is an abstract class.  Only child classes should be instantiated.
    """

    def __str__(self):
        """Return a string representation.

        @type self: Container
        """
        raise NotImplementedError("Implemented in a subclass")

    def add(self, item):
        """Add <item> to this Container.

        @type self: Container
        @type item: Object
        @rtype: None
        """
        raise NotImplementedError("Implemented in a subclass")

    def remove(self):
        """Remove and return a single item from this Container.

        @type self: Container
        @rtype: Object
        """
        raise NotImplementedError("Implemented in a subclass")

    def is_empty(self):
        """Return True iff this Container is empty.

        @type self: Container
        @rtype: bool
        """
        raise NotImplementedError("Implemented in a subclass")


class PriorityQueue(Container):
    """A queue of items that operates in priority order.

    Items are removed from the queue according to priority; the item with the
    highest priority is removed first. Ties are resolved in FIFO order,
    meaning the item which was inserted *earlier* is the first one to be
    removed.

    Priority is defined by the rich comparison methods for the objects in the
    container (__lt__, __le__, __gt__, __ge__).

    If x < y, then x has a *HIGHER* priority than y.

    All objects in the container must be of the same type.
    """

    # === Private Attributes ===
    # @type _items: list
    #     The items stored in the priority queue.
    #
    # === Representation Invariants ===
    # _items is a sorted list, where the first item in the queue is the
    # item with the highest priority.

    def __init__(self):
        """Initialize an empty PriorityQueue.

        @type self: PriorityQueue
        @rtype: None
        """
        self._items = []

    def __str__(self):
        """Return a string representation.

        @type self: PriorityQueue
        @rtype str

        >>> pq = PriorityQueue()
        >>> pq.add(2)
        >>> pq.add(1)
        >>> print(pq)
        [1, 2]
        """
        return str(self._items)

    def remove(self):
        """Remove and return the next item from this PriorityQueue.

        Precondition: <self> should not be empty.

        @type self: PriorityQueue
        @rtype: object

        >>> pq = PriorityQueue()
        >>> pq.add("red")
        >>> pq.add("blue")
        >>> pq.add("yellow")
        >>> pq.add("green")
        >>> pq.remove()
        'blue'
        >>> pq.remove()
        'green'
        >>> pq.remove()
        'red'
        >>> pq.remove()
        'yellow'
        """
        return self._items.pop(0)

    def is_empty(self):
        """
        Return true iff this PriorityQueue is empty.

        @type self: PriorityQueue
        @rtype: bool

        >>> pq = PriorityQueue()
        >>> pq.is_empty()
        True
        >>> pq.add("thing")
        >>> pq.is_empty()
        False
        """
        return len(self._items) == 0

    def add(self, item):
        """Add <item> to this PriorityQueue.

        @type self: PriorityQueue
        @type item: object
        @rtype: None

        >>> pq = PriorityQueue()
        >>> pq.add("yellow")
        >>> pq.add("blue")
        >>> pq.add("red")
        >>> pq.add("green")
        >>> pq._items
        ['blue', 'green', 'red', 'yellow']
        """
        self._items.append(item)
        self._items.sort()

class Queue(Container):
    """
    A first-in, first-out (FIFO) queue.
    """

    def __init__(self):
        """
        Create and initialize new Queue self.

        @param Queue self: this queue
        @rtype: None
        """
        self._items = []

    def __str__(self):
        """Return a string representation.

        @type self: Queue
        @rtype str

        >>> q = Queue()
        >>> q.add(1)
        >>> q.add(2)
        >>> print(q)
        >>> '[2, 1]'
        """
        return str(self._items)

    def add(self, obj):
        """
        Add object at the back of Queue self.

        @param Queue self: this queue
        @param object obj: object to add
        @rtype: None
        """
        self._items.append(obj)

    def remove(self, object=None):
        """
        Remove and return object. If Queue is empty, do nothing.

        Queue self must not be empty.

        @param Queue self: this Queue
        @rtype: None | object

        >>> q = Queue()
        >>> q.add(3)
        >>> q.add(5)
        >>> q.remove()
        3
        """
        if object != None:
            if object not in self._items:
                raise NotImplementedError('Item not found')
            self._items.remove(object)

        return self._items.pop(0)

    def is_empty(self):
        """
        Return whether Queue self is empty

        @param Queue self:
        @rtype: bool

        >>> q = Queue()
        >>> q.add(5)
        >>> q.is_empty()
        False
        >>> q.remove()
        5
        >>> q.is_empty()
        True
        """
        return self._items == []
