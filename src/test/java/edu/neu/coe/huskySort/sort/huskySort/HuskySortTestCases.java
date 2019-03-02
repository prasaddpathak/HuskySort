package edu.neu.coe.huskySort.sort.huskySort;

import edu.neu.coe.huskySort.sort.huskySortUtils.HuskyCoder;
import edu.neu.coe.huskySort.sort.huskySortUtils.HuskySortHelper;
import edu.neu.coe.huskySort.sort.huskySortUtils.HuskySortable;
import edu.neu.coe.huskySort.sort.Sort;
import edu.neu.coe.huskySort.util.PrivateMethodTester;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HuskySortTestCases {

    @Test
    public void testGetWords() {
        final PrivateMethodTester tester = new PrivateMethodTester(HuskySortTest.class);
        final List<String> words = (List<String>) tester.invokePrivate("getWords", Pattern.compile("[~\\t]*\\t((\\s*[a-zA-Z]*)*)"), "11204341\tConsider the extras not usually included with any 'FREE' car offer: CDW/LDW @ $12.95 to $13.95 a day.\n");
        assertEquals(8, words.size());
    }

    class Person implements HuskySortable<Person> {

        Person(String firstName, String lastName) {
            this.lastName = lastName;
            this.firstName = firstName;
        }

        @Override
        public long huskyCode() {
            if (huskycode==0L)
                huskycode = HuskySortHelper.asciiToLong(lastName);
            return huskycode;
        }

        @Override
        public int compareTo(Person x) {
            int cf = lastName.compareTo(x.lastName);
            if (cf != 0) return cf;
            return firstName.compareTo(x.firstName);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(lastName, person.lastName) &&
                    Objects.equals(firstName, person.firstName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(lastName, firstName);
        }

        @Override
        public String toString() {
            return lastName;
        }

        private final String lastName;
        private final String firstName;

        private long huskycode = 0L;
    }

    @Test
    public void testSortPerson() {
        Person[] xs = {new Person("Robin", "Hillyard"), new Person("Yunlu", "Liao Zheng"), new Person("Miranda", "Hillyard"), new Person("William", "Hillyard"), new Person("Ella", "Hillyard"), new Person("Paul", "Hillyard"), new Person("Mia", "Hillyard")};
        QuickHuskySort<Person> sorter = new QuickHuskySort<>();
        sorter.sort(xs, new HuskyCoder<Person>() {
            @Override
            public long huskyEncode(Person person) {
                return person.huskyCode();
            }

            @Override
            public boolean imperfect() {
                return true;
            }
        });
        assertTrue(sorter.getHelper().sorted(xs));
    }

    @Test
    public void testSortString() {
        String[] xs = {"Hello", "Goodbye", "Ciao", "Welkommen"};
        QuickHuskySort<String> sorter = new QuickHuskySort<>();
        sorter.sort(xs, HuskySortHelper.asciiCoder);
        assertTrue(sorter.getHelper().sorted(xs));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testSortOldDate() {
        Date[] xs = {new Date(2018, 11, 9), new Date(2018, 11, 6), new Date(2018, 10, 31), new Date(2018, 1, 1)};
        Sort<Date> sorter = new edu.neu.coe.huskySort.sort.huskySortUtils.HuskySort<>(HuskySortHelper.dateCoder);
        Date[] sorted = sorter.sort(xs, false);
        assertTrue(sorter.getHelper().sorted(sorted));
    }

    @Test
    public void testSortJavaTime() {
        ChronoLocalDateTime<?> d1 = LocalDateTime.of(2018, 11, 6, 10, 6, 45);
        ChronoLocalDateTime<?> d2 = LocalDateTime.of(2018, 11, 9, 22, 3, 15);
        ChronoLocalDateTime<?> d3 = LocalDateTime.of(1963, 11, 22, 13, 30, 0);
        ChronoLocalDateTime<?> d4 = LocalDateTime.of(2018, 10, 31, 22, 3, 15);
        ChronoLocalDateTime<?> d5 = LocalDateTime.of(2018, 1, 1, 0, 0, 0);
        ChronoLocalDateTime<?>[] xs = {d1, d2, d3, d4, d5};
        Sort<ChronoLocalDateTime<?>> sorter = new edu.neu.coe.huskySort.sort.huskySortUtils.HuskySort<>(HuskySortHelper.chronoLocalDateTimeCoder);
        ChronoLocalDateTime<?>[] sorted = sorter.sort(xs, false);
        assertTrue(sorter.getHelper().sorted(sorted));
    }
}