
package org.jaysonfong.loan.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.jaysonfong.loan.service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _MonthlyPayment_QNAME = new QName("http://service.loan.jaysonfong.org/", "MonthlyPayment");
    private final static QName _MonthlyPaymentResponse_QNAME = new QName("http://service.loan.jaysonfong.org/", "MonthlyPaymentResponse");
    private final static QName _TotalPayment_QNAME = new QName("http://service.loan.jaysonfong.org/", "TotalPayment");
    private final static QName _TotalPaymentResponse_QNAME = new QName("http://service.loan.jaysonfong.org/", "TotalPaymentResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.jaysonfong.loan.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MonthlyPayment }
     * 
     */
    public MonthlyPayment createMonthlyPayment() {
        return new MonthlyPayment();
    }

    /**
     * Create an instance of {@link MonthlyPaymentResponse }
     * 
     */
    public MonthlyPaymentResponse createMonthlyPaymentResponse() {
        return new MonthlyPaymentResponse();
    }

    /**
     * Create an instance of {@link TotalPayment }
     * 
     */
    public TotalPayment createTotalPayment() {
        return new TotalPayment();
    }

    /**
     * Create an instance of {@link TotalPaymentResponse }
     * 
     */
    public TotalPaymentResponse createTotalPaymentResponse() {
        return new TotalPaymentResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MonthlyPayment }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MonthlyPayment }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.loan.jaysonfong.org/", name = "MonthlyPayment")
    public JAXBElement<MonthlyPayment> createMonthlyPayment(MonthlyPayment value) {
        return new JAXBElement<MonthlyPayment>(_MonthlyPayment_QNAME, MonthlyPayment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MonthlyPaymentResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link MonthlyPaymentResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.loan.jaysonfong.org/", name = "MonthlyPaymentResponse")
    public JAXBElement<MonthlyPaymentResponse> createMonthlyPaymentResponse(MonthlyPaymentResponse value) {
        return new JAXBElement<MonthlyPaymentResponse>(_MonthlyPaymentResponse_QNAME, MonthlyPaymentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TotalPayment }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TotalPayment }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.loan.jaysonfong.org/", name = "TotalPayment")
    public JAXBElement<TotalPayment> createTotalPayment(TotalPayment value) {
        return new JAXBElement<TotalPayment>(_TotalPayment_QNAME, TotalPayment.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TotalPaymentResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link TotalPaymentResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.loan.jaysonfong.org/", name = "TotalPaymentResponse")
    public JAXBElement<TotalPaymentResponse> createTotalPaymentResponse(TotalPaymentResponse value) {
        return new JAXBElement<TotalPaymentResponse>(_TotalPaymentResponse_QNAME, TotalPaymentResponse.class, null, value);
    }

}
