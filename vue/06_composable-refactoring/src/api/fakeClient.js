const delay = (data, ms = 400) => {
    return new Promise((resolve) => {
        setTimeout(() => {
            const cloned = JSON.parse(JSON.stringify(data));
            resolve(cloned);
        }, ms);
    });
};

export { delay };