export const formatCurrency = (value) => {
    const formater = new Intl.NumberFormat("en-US", {
        style: "currency",
        currency: "USD",
    });
    return formater.format(value);
};