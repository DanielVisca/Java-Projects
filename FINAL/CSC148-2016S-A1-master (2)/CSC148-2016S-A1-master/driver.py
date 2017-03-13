from location import Location, manhattan_distance
from rider import Rider


class Driver:
    """A driver for a ride-sharing service.

    === Attributes ===
    @type id: str
        A unique identifier for the driver.
    @type location: Location
        The current location of the driver.
    @type is_idle: bool
        A property that is True if the driver is idle and False otherwise.
    """

    def __init__(self, identifier, location, speed):
        """Initialize a Driver.

        @type self: Driver
        @type identifier: str
        @type location: Location
        @type speed: int
        @type destination: Location
        @type passenger: str
        @rtype: None
        """
        self.id = identifier
        self.location = location
        self.speed = speed
        self.is_idle = True
        self.destination = None
        self._passenger = None

    def __str__(self):
        """Return a string representation.

        @type self: Driver
        @rtype: str
        """
        return "{}".format(self.id)

    def __eq__(self, other):
        """Return True if self equals other, and false otherwise.

        @type self: Driver
        @rtype: bool
        """
        return type(self) == type(other) and \
               self.id == other.id and \
               self.location == other.location and \
               self.speed == other.speed and \
               self.is_idle == other.is_idle

    def __lt__(self, other):
        """Return True if self is less than other, and False otherwise.

        @type self: Driver
        @type other: Driver
        @rtype: bool
        """
        return self.speed < other.speed

    def __le__(self, other):
        """Return True if self is less than or equal to other, and False
        otherwise.

        @type self: Driver
        @type other: Driver
        @rtype: bool
        """
        return self.speed <= other.speed

    def __gt__(self, other):
        """Return True if self is greater than other, and False otherwise.

        @type self: Driver
        @type other: Driver
        @rtype: bool
        """
        return self.speed > other.speed

    def __ge__(self, other):
        """Return True if self is greater than or equal to other, and False
        otherwise.

        @type self: Driver
        @type other: Driver
        @rtype: bool
        """
        return self.speed >= other.speed

    def get_travel_time(self, destination):
        """Return the time it will take to arrive at the destination,
        rounded to the nearest integer.

        @type self: Driver
        @type destination: Location
        @rtype: int
        """
        return round(manhattan_distance(self.location, destination) / self.speed)

    def start_drive(self, location):
        """Start driving to the location and return the time the drive will
        take.

        @type self: Driver
        @type location: Location
        @rtype: int
        """
        self.destination = location
        return self.get_travel_time(self.destination)

    def end_drive(self):
        """End the drive and arrive at the destination.

        Precondition: self.destination is not None.

        @type self: Driver
        @rtype: None
        """
        # Set self's location to self.destination at end of drive
        self.location = self.destination

    def start_ride(self, rider):
        """Start a ride and return the time the ride will take. The driver
        learns the ID of the rider as its passenger.

        @type self: Driver
        @type rider: Rider
        @rtype: int
        """
        # Learns identity of the rider and destination
        self._passenger = rider.id
        self.destination = rider.destination
        self.is_idle = False

        # Returns travel_time to rider's destination
        return self.get_travel_time(rider.destination)

    def end_ride(self):
        """End the current ride, and arrive at the rider's destination. The
        driver loses its passenger and becomes idle again.

        Precondition: The driver has a rider.
        Precondition: self.destination is not None.

        @type self: Driver
        @rtype: None
        """
        # Sets driver passenger to None
        self._passenger = None
        # Set driver location to rider destination
        self.location = self.destination
        self.destination = None
        # Set the driver to idle again
        self.is_idle = True