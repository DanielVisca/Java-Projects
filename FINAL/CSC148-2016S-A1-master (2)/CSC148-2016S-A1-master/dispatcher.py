from driver import Driver
from rider import Rider, WAITING, CANCELLED, SATISFIED
from container import Queue, PriorityQueue
from location import Location


class Dispatcher:
    """A dispatcher fulfills requests from riders and drivers for a
    ride-sharing service.

    When a rider requests a driver, the dispatcher assigns a driver to the
    rider. If no driver is available, the rider is placed on a waiting
    list for the next available driver. A rider that has not yet been
    picked up by a driver may cancel their request.

    When a driver requests a rider, the dispatcher assigns a rider from
    the waiting list to the driver. If there is no rider on the waiting list
    the dispatcher does nothing. Once a driver requests a rider, the driver
    is registered with the dispatcher, and will be used to fulfill future
    rider requests.
    """

    def __init__(self):
        """Initialize a Dispatcher.

        @type self: Dispatcher
        @type _waitlist: Queue of Rider
        @type _fleet: PriorityQueue of Driver
        @rtype: None
        """
        self._waitlist = Queue()
        self._fleet = []

    def request_driver(self, rider):
        """Return a driver for the rider, or None if no driver is available.

        Add the rider to the waiting list if there is no available driver.

        @type self: Dispatcher
        @type rider: Rider
        @rtype: Driver | None

        >>> d = Dispatcher()
        >>> d1 = Driver('a', Location(9,0), 1)
        >>> d2 = Driver('b', Location(0,0), 1)
        >>> d._fleet = [d1, d2]
        >>> r1 = Rider('a', Location(0,0), Location(1,0), 3)
        >>> print(d.request_driver(r1))
        a
        """
        fastest_driver = self._fleet[0]

        for driver in self._fleet:
            if not driver.is_idle:
                self._waitlist.add(rider)
                return None
            else:
                for i in range(len(self._fleet) - 1):
                    for j in range(len(self._fleet) - 1):
                        if self._fleet[i].get_travel_time(rider.origin) \
                                < self._fleet[j].get_travel_time(rider.origin):
                            fastest_driver = self._fleet[i]
                        else:
                            fastest_driver = self._fleet[j]

        return fastest_driver

    def request_rider(self, driver):
        """Return a rider for the driver, or None if no rider is available.

        If this is a new driver, register the driver for future rider requests.

        @type self: Dispatcher
        @type driver: Driver
        @rtype: Rider | None

        >>> d = Dispatcher()
        >>> r1 = Rider('a', Location(0,0), Location(1,0), 3)
        >>> d._waitlist = Queue()
        >>> d._waitlist.add(r1)
        >>> d1 = Driver('a', Location(0,0), 1)
        >>> d2 = Driver('b', Location(0,0), 1)
        >>> d._fleet = [d1]
        >>> print(d.request_rider(d2))
        a
        >>> print(d._fleet)
        """
        if driver not in self._fleet:
            self._fleet.append(driver)

        # If waitlist is not empty, assign a rider
        if not self._waitlist.is_empty():
            return self._waitlist.remove()
        else:
            return None

    def cancel_ride(self, rider):
        """Cancel the ride for rider and change their status to CANCELLED.

        @type self: Dispatcher
        @type rider: Rider
        @rtype: None
        """
        rider._status = CANCELLED
        self._waitlist.remove(rider)
