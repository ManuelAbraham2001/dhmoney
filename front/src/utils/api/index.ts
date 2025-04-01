import { UserAccount, User, Transaction, Card } from '../../types';

const myInit = (method = 'GET', token?: string) => {
  return {
    method,
    headers: {
      'Content-Type': 'application/json',
      Authorization: token ? `Bearer ${token}` : '',
    },
    mode: 'cors' as RequestMode,
    cache: 'default' as RequestCache,
  };
};

const myRequest = (endpoint: string, method: string, token?: string) =>
  new Request(endpoint, myInit(method, token));

const baseUrl = 'http://localhost:8085';

const rejectPromise = (response?: Response): Promise<Response> =>
  Promise.reject({
    status: (response && response.status) || '00',
    statusText: (response && response.statusText) || 'Ocurrió un error',
    err: true,
  });

export const login = (email: string, password: string) => {
  return fetch(myRequest(`${baseUrl}/api/auth/login`, 'POST'), {
    body: JSON.stringify({ email, password }),
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      return rejectPromise(response);
    })
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const logoutApi = (token: string) => {
  return fetch(myRequest(`${baseUrl}/api/auth/logout`, 'POST', token))
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      return rejectPromise(response);
    })
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const createAnUser = (user: User) => {
  return fetch(myRequest(`${baseUrl}/api/auth/register`, 'POST'), {
    body: JSON.stringify(user),
  })
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      return rejectPromise(response);
    })
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const getUser = (id: string, token: string): Promise<User> => {
  return fetch(myRequest(`${baseUrl}/api/users/${id}`, 'GET', token))
    .then((response) =>
      response.ok ? response.json() : rejectPromise(response)
    )
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const updateUser = (
  id: string,
  data: any,
  token: string
): Promise<Response> => {
  return fetch(myRequest(`${baseUrl}/api/users/${id}`, 'PATCH', token), {
    body: JSON.stringify(data),
  })
    .then((response) =>
      response.ok ? response.json() : rejectPromise(response)
    )
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const getAccount = (id: string, token: string): Promise<UserAccount> => {
  return fetch(myRequest(`${baseUrl}/api/accounts/${id}`, 'GET', token), {})
    .then((response) => {
      if (response.ok) {
        return response.json().then((account) => account);
      }
      return rejectPromise(response);
    })
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const getAccountByCvuOrAlias = (cvuOrAlias: string, token: string): Promise<UserAccount> => {
  return fetch(myRequest(`${baseUrl}/api/accounts/cvu/alias/${cvuOrAlias}`, 'GET', token), {})
    .then((response) => {
      if (response.ok) {
        return response.json().then((account) => account);
      }
      return rejectPromise(response);
    })
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const getAccounts = (token: string): Promise<UserAccount[]> => {
  return fetch(myRequest(`${baseUrl}/api/accounts`, 'GET', token), {})
    .then((response) =>
      response.ok ? response.json() : rejectPromise(response)
    )
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const updateAccount = (
  id: string,
  data: any,
  token: string
): Promise<Response> => {
  return fetch(myRequest(`${baseUrl}/api/accounts/${id}`, 'PATCH', token), {
    body: JSON.stringify(data),
  })
    .then((response) =>
      response.ok ? response.json() : rejectPromise(response)
    )
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const getUserActivities = (
  userId: string,
  token: string,
  limit?: number
): Promise<Transaction[]> => {
  return fetch(
    myRequest(
      `${baseUrl}/api/activities/users/${userId}${limit ? `?limit=${limit}` : ''}`,
      'GET',
      token
    )
  )
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      return rejectPromise(response);
    })
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const getUserActivity = (
  userId: string,
  activityId: string,
  token: string
): Promise<Transaction> => {
  return fetch(
    myRequest(
      `${baseUrl}/api/activities/${activityId}`,
      'GET',
      token
    )
  )
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      return rejectPromise(response);
    })
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const getUserCards = (
  userId: string,
  token: string
): Promise<Card[]> => {
  return fetch(myRequest(`${baseUrl}/api/cards/${userId}`, 'GET', token))
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      return rejectPromise(response);
    })
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const getUserCard = (userId: string, cardId: string): Promise<Card> => {
  return fetch(myRequest(`${baseUrl}/users/${userId}/cards/${cardId}`, 'GET'))
    .then((response) => {
      if (response.ok) {
        return response.json();
      }
      return rejectPromise(response);
    })
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const deleteUserCard = (
  userId: string,
  cardId: string,
  token: string
): Promise<Response> => {
  return fetch(
    myRequest(`${baseUrl}/api/cards/${cardId}`, 'DELETE', token)
  )
    .then((response) => {
      if (response.ok) {
        window.location.reload();
        // return response.json();
      }
      return rejectPromise(response);
    })
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const createUserCard = (
  userId: string,
  card: any,
  token: string
): Promise<Response> => {
  return fetch(myRequest(`${baseUrl}/api/cards/${userId}`, 'POST', token), {
    body: JSON.stringify(card),
  })
    .then((response) =>
      response.ok ? response.json() : rejectPromise(response)
    )
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

// TODO: edit when backend is ready
export const createDepositActivity = (
  userId: string,
  amount: number,
  token: string
) => {
  // const maxAmount = 30000;
  // if (amount > maxAmount) return rejectPromise();

  const activity = {
    amount,
    type: 'Deposit',
    description: 'Depósito con tarjeta',
    // dated: new Date(), // date must be genarated in backend
  };

  return fetch(
    myRequest(`${baseUrl}/api/activities/${userId}`, 'POST', token),
    {
      body: JSON.stringify(activity),
    }
  )
    .then((response) =>
      response.ok ? response.json() : rejectPromise(response)
    )
    // .then((data) => {
    //   depositMoney(data.amount, userId, token);
    // })
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};

export const createTransferActivity = (
  userId: string,
  token: string,
  origin: string,
  destination: string,
  amount: number,
  name?: string
) => {
  return fetch(
    myRequest(`${baseUrl}/api/activities/${userId}`, 'POST', token),
    {
      body: JSON.stringify({
        type: 'Transfer',
        amount: amount * -1,
        origin,
        destination,
        name,
        // dated: new Date(), // date must be genarated in backend
      }),
    }
  )
    .then((response) =>
      response.ok ? response.json() : rejectPromise(response)
    )
    .catch((err) => {
      console.log(err);
      return rejectPromise(err);
    });
};
