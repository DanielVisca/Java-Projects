"""
The rider module contains the Rider class. It also contains
constants that represent the status of the rider.

=== Constants ===
@type WAITING: str
    A constant used for the waiting rider status.
@type CANCELLED: str
    A constant used for the cancelled rider status.
@type SATISFIED: str
    A constant used for the satisfied rider status
"""

WAITING = "waiting"
CANCELLED = "cancelled"
SATISFIED = "satisfied"


class Rider:
    """A rider for a ride-sharing service.

    === Attributes ===
    @type id: str
        A unique identifier for the rider.
    @type origin: Location
        The rider's pick-up location.
    @type destination: Location
        The rider's drop-off location.
    @type patience: int
        The number of minutes the rider is willing to wait
        before cancelling their ride request.
    @type status: str
        The rider's status, which may be WAITING, CANCELLED, or
        SATISFIED.
    """

    def __init__(self, identifier, origin, destination, patience):
        """Initialize a Rider.

        @type self: Rider
        @type identifier: str
        @type origin: Location
        @type destination: Location
        @type patience: int
        @rtype: None
        """
        self.id = identifier
        self.origin = origin
        self.destination = destination
        self.patience = patience
        self._status = WAITING

    def __str__(self):
        """Return a string representation.

        @type self: Rider
        @rtype: str
        """
        return '{}'.format(self.id)

    def __eq__(self, other):
        """Return True if self equals other, and False otherwise.

        @type self: Rider
        @type other: Rider
        @rtype: bool
        """
        return type(self) and \
               self.id and \
               self.origin and \
               self.destination, \
               self.patience, self._status == type(other), other.id, \
               other.origin, other.destination, other.patience, other._status

    def __lt__(self, other):
        """Return True if self is less than other, and False otherwise.

        @type self: Rider
        @type other: Rider
        @rtype: bool
        """
        return self.patience < other.patience

    def __le__(self, other):
        """Return True if self is less than or equal to other, and False
        otherwise.

        @type self: Rider
        @type other: Rider
        @rtype: bool
        """
        return self.patience <= other.patience

    def __gt__(self, other):
        """Return True if self is greater than other, and False otherwise.

        @type self: Rider
        @type other: Rider
        @rtype: bool
        """
        return self.patience > other.patience

    def __ge__(self, other):
        """Return True if self is greater than or equal to other, and False
        otherwise.

        @type self: Rider
        @type other: Rider
        @rtype: bool
        """
        return self.patience >= other.patience
