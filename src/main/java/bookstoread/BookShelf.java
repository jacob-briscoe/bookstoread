package bookstoread;

import java.time.Year;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class BookShelf {

    private final List<Book> books = new ArrayList<>();

    public List<Book> books() {
        return Collections.unmodifiableList(books);
    }

    public void add(final Book... booksToAdd) {
        Arrays.stream(booksToAdd).forEach(books::add);
    }

    public List<Book> arrange() {
        return arrange(Comparator.naturalOrder());
    }

    public List<Book> arrange(final Comparator<Book> criteria) {
        return books.stream().sorted(criteria).collect(Collectors.toList());
    }

    public Map<Year, List<Book>> groupByPublicationYear() {
        return groupBy(book -> Year.of(book.getPublishedOn().getYear()));
    }

    public <K> Map<K, List<Book>> groupBy(final Function<Book, K> fx) {
        return books.stream().collect(groupingBy(fx));
    }

    public Progress progress() {
        final int booksRead = Long.valueOf(books.stream().filter(Book::isRead).count()).intValue();
        final int booksToRead = books.size() - booksRead;
        final int percentageCompleted = booksRead * 100 / books.size();
        final int percentageToRead = booksToRead * 100 / books.size();

        return new Progress(percentageCompleted, percentageToRead, 0);
    }
}
