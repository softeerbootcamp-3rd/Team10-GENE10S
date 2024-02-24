import React, { useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import axios from '../api/Settings';

const RedirectPage = () => {
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const params = new URLSearchParams(location.search);
        const authorizationCode = params.get('code');
        const state = params.get('state');

        if (authorizationCode) {
          console.log('Authorization Code:', authorizationCode);
          console.log('State:', state);

          await axios.post('/v1/login', {
            grantType: 'authorization_code',
            code: authorizationCode,
            redirectUri: process.env.REACT_APP_REDIRECT_URI_CLIENT + '/oauth/redirect',
            clientId: process.env.REACT_APP_CLIENT_ID,
            clientSecret: process.env.REACT_APP_CLIENT_SECRET,
          });

          navigate('/');
        } else {
          console.error('Authorization Code not found');
        }
      } catch (error) {
        console.error('Error:', error);
      }
    };

    fetchData();
  }, [navigate, location]);

  return (
    <div>
      <p>Redirecting...</p>
    </div>
  );
};

export default RedirectPage;
