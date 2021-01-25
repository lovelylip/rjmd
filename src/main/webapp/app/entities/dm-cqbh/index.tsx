import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import DmCqbh from './dm-cqbh';
import DmCqbhDetail from './dm-cqbh-detail';
import DmCqbhUpdate from './dm-cqbh-update';
import DmCqbhDeleteDialog from './dm-cqbh-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DmCqbhUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DmCqbhUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DmCqbhDetail} />
      <ErrorBoundaryRoute path={match.url} component={DmCqbh} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DmCqbhDeleteDialog} />
  </>
);

export default Routes;
