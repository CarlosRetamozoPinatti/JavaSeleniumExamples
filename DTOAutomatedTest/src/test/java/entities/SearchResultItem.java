package entities;
/*
A data transfer object (DTO) is an object that carries data between processes.
You can use this technique to facilitate communication between two systems
(like an API and your server) without potentially exposing sensitive information.
 */
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SearchResultItem {

    private final String title;

    private final String hrefValue;

    public SearchResultItem(String title, String hrefValue) {
        this.title = title;
        this.hrefValue = hrefValue;
    }

    public String getTitle() {
        return title;
    }

    public String getHrefValue() {
        return hrefValue;
    }
/*
The equals() and hashCode() methods.
These two methods are defined in the java.lang.Object class.
We use the equals() method to compare whether two objects are equivalent,
which means that the two objects themselves (not the references) are equal.
To check whether the object references are equal or not,
we use the == operator which uses the hash code value of the object to evaluate it.

The hashCode() method is used to generate a hash code value for an object
and this hash code value is used by some collection classes to compare objects,
which increases the performance of large collections of objects.

We need to override both methods in order to make the Assertions work in the test,
Because if we don't, the Assertions will compare Objects instead of the reference,
and it will always fail.
 */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SearchResultItem that = (SearchResultItem) o;

        return new EqualsBuilder()
                .append(title, that.title)
                .append(hrefValue, that.hrefValue)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(hrefValue)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("title", title)
                .append("hrefValue", hrefValue)
                .toString();
    }
}