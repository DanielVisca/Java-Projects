from location import Location
from driver import Driver, manhattan_distance
from rider import Rider

"""
The Monitor module contains the Monitor class, the Activity class,
and a collection of constants. Together the elements of the module
help keep a record of activities that have occurred.

Activities fall into two categories: Rider activities and Driver
activities. Each activity also has a description, which is one of
request, cancel, pickup, or dropoff.

=== Constants ===
@type RIDER: str
    A constant used for the Rider activity category.
@type DRIVER: str
    A constant used for the Driver activity category.
@type REQUEST: str
    A constant used for the request activity description.
@type CANCEL: str
    A constant used for the cancel activity description.
@type PICKUP: str
    A constant used for the pickup activity description.
@type DROPOFF: str
    A constant used for the dropoff activity description.
"""

RIDER = "rider"
DRIVER = "driver"

REQUEST = "request"
CANCEL = "cancel"
PICKUP = "pickup"
DROPOFF = "dropoff"


class Activity:
    """An activity that occurs in the simulation.

    === Attributes ===
    @type timestamp: int
        The time at which the activity occurred.
    @type description: str
        A description of the activity.
    @type identifier: str
        An identifier for the person doing the activity.
    @type location: Location
        The location at which the activity occurred.
    """

    def __init__(self, timestamp, description, identifier, location):
        """Initialize an Activity.

        @type self: Activity
        @type timestamp: int
        @type description: str
        @type identifier: str
        @type location: Location
        @rtype: None
        """
        self.description = description
        self.time = timestamp
        self.id = identifier
        self.location = location

    def __str__(self):
        """Return a string representation of self.

        @type self: Activity
        @rtype: str

        >>> Activity(1, 'RiderRequest', 'Daniel', 1,1)
        >>> Activity.__str__()
        '1, RiderRequest, Daniel, 1,1'
        
        """
        return '{} - {} {} {}'.format(str(self.time), self.description, self.id,
                                          str(self.location))

class Monitor:
    """A monitor keeps a record of activities that it is notified about.
    When required, it generates a report of the activities it has recorded.
    """

    # === Private Attributes ===
    # @type _activities: dict[str, dict[str, list[Activity]]]
    #       A dictionary whose key is a category, and value is another
    #       dictionary. The key of the second dictionary is an identifier
    #       and its value is a list of Activities.

    def __init__(self):
        """Initialize a Monitor.

        @type self: Monitor
        """
        self._activities = {
            RIDER: {},
            DRIVER: {}
        }
        """@type _activities: dict[str, dict[str, list[Activity]]]"""

    def __str__(self):
        """Return a string representation.

        @type self: Monitor
        @rtype: str
        
        >>> Monitor = {RIDER:'ME', DRIVER:'ALSO ME'}
        >>> Monitor.__str__()
        "{'driver': 'ALSO ME', 'rider': 'ME'}"
        """
        return "Monitor ({} drivers, {} riders)".format(
                len(self._activities[DRIVER]), len(self._activities[RIDER]))

    def notify(self, timestamp, category, description, identifier, location):
        """Notify the monitor of the activity.

        @type self: Monitor
        @type timestamp: int
            The time of the activity.
        @type category: DRIVER | RIDER
            The category for the activity.
        @type description: REQUEST | CANCEL | PICKUP | DROPOFF
            A description of the activity.
        @type identifier: str
            The identifier for the actor.
        @type location: Location
            The location of the activity.
        @rtype: None
        """
        if identifier not in self._activities[category]:
            self._activities[category][identifier] = []

        activity = Activity(timestamp, description, identifier, location)
        self._activities[category][identifier].append(activity)

    def report(self):
        """Return a report of the activities that have occurred.

        @type self: Monitor
        @rtype: dict[str, object]
        """
        return {"rider_wait_time": self._average_wait_time(),
                "driver_total_distance": self._average_total_distance(),
                "driver_ride_distance": self._average_ride_distance()}

    def _average_wait_time(self):
        """Return the average wait time of riders that have either been picked
        up or have cancelled their ride.

        @type self: Monitor
        @rtype: float
        """
        wait_time = 0
        count = 0

        # Iterate through activities list of each rider
        for rider in self._activities[RIDER]:
            # Add to total waiting riders (all riders will wait - they are
            # created at the time of requesting a ride)
            count += 1
            # For each activity in the rider's list
            for i in range(len(self._activities[RIDER][rider]) - 1):
                # Find REQUEST event
                if self._activities[RIDER][rider][i].description == REQUEST:
                    # Wait time is time between REQUEST and next event
                    # (PICKUP, CANCEL, and DROPOFF are not followed by waiting)
                    wait_time += self._activities[RIDER][rider][i + 1].time - \
                           self._activities[RIDER][rider][i].time

        if count != 0:
            return wait_time / count
        else:
            return 0

    def _average_total_distance(self):

        """Return the average distance drivers have driven.

        @type self: Monitor
        @rtype: float
        """
        total_dist = 0
        count = 1

        # Iterate through activities list of each driver
        for driver in range(len(self._activities[DRIVER]) - 1):
            # Add driver to total count
            count += 1
            # Find each driver's total distance
            total_dist += self.get_riderless_distance(driver) \
                          + self.get_ride_distance(driver)

        if count != 0:
            return total_dist / count
        else:
            return 0

    def _average_ride_distance(self):
        """Return the average distance drivers have driven on rides.

        @type self: Monitor
        @rtype: float
        """
        total_dist = 0
        count = 0

        # Iterate through activities of drivers
        for driver in self._activities[DRIVER]:
            # Add driver to total count
            count += 1
            # Get driver's total ride distance
            total_dist += self.get_ride_distance(driver)

        if count != 0:
            return total_dist / count
        else:
            return 0

    def get_riderless_distance(self, driver):
        """Return the total distance travelled without a rider by driver.

        @type self: Monitor
        @type driver: str
        @rtype: int
        """
        total_dist = 0

        for driver in self._activities[DRIVER]:
            # Scan through activities list
            for i in range(len(self._activities[DRIVER][driver]) - 2):
                current = self._activities[DRIVER][driver][i]
                next = self._activities[DRIVER][driver][i + 1]
                # If activity is REQUEST
                if REQUEST in current.description:
                    # Add distance between activity and next activity to total
                    total_dist += manhattan_distance(current.location,
                                                     next.location)

        # Return total riderless distance
        return total_dist

    def get_ride_distance(self, driver):
        """Return the total distance travelled with a rider by driver.

        @type self: Monitor
        @type driver: str
        @rtype: int
        """
        total_dist = 0

        for driver in self._activities[DRIVER]:
            # Scan through activities list
            for i in range(len(self._activities[DRIVER][driver]) - 1):
                current = self._activities[DRIVER][driver][i]
                next = self._activities[DRIVER][driver][i + 1]
                # If activity is PICKUP followed by DROPOFF
                if PICKUP in current.description and DROPOFF in next.description:
                # Add distance between activity and next activity to total
                    dist = manhattan_distance(current.location, next.location)
                    total_dist += dist

        # Return total distance with rider
        return total_dist

# if __name__ == '__main__':
#     m = Monitor()
#     m.notify(1, DRIVER, REQUEST, 'a', Location(0,0))
#     m.notify(2, DRIVER, PICKUP, 'a', Location(1,1))
#     print(m.get_riderless_distance('a'))
#     m.notify(3, DRIVER, DROPOFF, 'a', Location(2,2))
#     print(m.get_riderless_distance('a'))
#     m.notify(4, DRIVER, REQUEST, 'a', Location(2,2))
#     m.notify(4, DRIVER, PICKUP, 'a', Location(3,6))
#     print(m.get_riderless_distance('a'))
#
