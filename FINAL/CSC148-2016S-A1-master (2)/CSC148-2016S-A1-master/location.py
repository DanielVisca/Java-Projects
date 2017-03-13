
class Location:
    def __init__(self, row, column):
        """Initialize a location.

        @type self: Location
        @type row: int
        @type column: int
        @rtype: None
        """
        self.row = row
        self.column = column

    def __str__(self):
        """Return a string representation.

        @rtype: str
        
        >>> Location = Location(1,1)
        >>> Location.__str__()
        '1,1'
        """
        return '{},{}'.format(str(self.row), str(self.column))

    def __eq__(self, other):
        """Return True if self equals other, and false otherwise.
        @type self: Location
        @type other: object
        @rtype: bool
        
        >>> self = Location(1,1)
        >>> other = Location.(1,1)
        >>> Location.__eq__(self,other)
        True
        
        >>> self = Location(1,1)
        >>> other = Location.(2,1)
        >>> Location.__eq__(self,other)
        False
        """
        return type(self) == type(other) and \
               self.row == other.row and \
               self.column == other.column

def manhattan_distance(origin, destination):
    """Return the Manhattan distance between the origin and the destination.

    @type origin: Location
    @type destination: Location
    @rtype: int

    >>> manhattan_distance(Location(0,0), Location(3,4))
    7
    >>> manhattan_distance(Location(3,4), Location(0,0))
    7
    >>> manhattan_distance(Location(3,4), Location(3,4))
    0
    """
    return abs(destination.row - origin.row) + \
           abs(destination.column - origin.column)


def deserialize_location(location_str):
    """Deserialize a location.

    @type location_str: str
        A location in the format 'row,col'
    @rtype: Location

    >>> deserialize_location('32,41')
    32,41
    >>> deserialize_location('0,0')
    0,0
    """
    row, col = location_str.split(',')
    return Location(int(row), int(col))
