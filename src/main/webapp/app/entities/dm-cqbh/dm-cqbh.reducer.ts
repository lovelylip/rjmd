import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDmCqbh, defaultValue } from 'app/shared/model/dm-cqbh.model';

export const ACTION_TYPES = {
  SEARCH_DMCQBHS: 'dmCqbh/SEARCH_DMCQBHS',
  FETCH_DMCQBH_LIST: 'dmCqbh/FETCH_DMCQBH_LIST',
  FETCH_DMCQBH: 'dmCqbh/FETCH_DMCQBH',
  CREATE_DMCQBH: 'dmCqbh/CREATE_DMCQBH',
  UPDATE_DMCQBH: 'dmCqbh/UPDATE_DMCQBH',
  DELETE_DMCQBH: 'dmCqbh/DELETE_DMCQBH',
  RESET: 'dmCqbh/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDmCqbh>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type DmCqbhState = Readonly<typeof initialState>;

// Reducer

export default (state: DmCqbhState = initialState, action): DmCqbhState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_DMCQBHS):
    case REQUEST(ACTION_TYPES.FETCH_DMCQBH_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DMCQBH):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_DMCQBH):
    case REQUEST(ACTION_TYPES.UPDATE_DMCQBH):
    case REQUEST(ACTION_TYPES.DELETE_DMCQBH):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.SEARCH_DMCQBHS):
    case FAILURE(ACTION_TYPES.FETCH_DMCQBH_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DMCQBH):
    case FAILURE(ACTION_TYPES.CREATE_DMCQBH):
    case FAILURE(ACTION_TYPES.UPDATE_DMCQBH):
    case FAILURE(ACTION_TYPES.DELETE_DMCQBH):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.SEARCH_DMCQBHS):
    case SUCCESS(ACTION_TYPES.FETCH_DMCQBH_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_DMCQBH):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_DMCQBH):
    case SUCCESS(ACTION_TYPES.UPDATE_DMCQBH):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_DMCQBH):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/dm-cqbhs';
const apiSearchUrl = 'api/_search/dm-cqbhs';

// Actions

export const getSearchEntities: ICrudSearchAction<IDmCqbh> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_DMCQBHS,
  payload: axios.get<IDmCqbh>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`),
});

export const getEntities: ICrudGetAllAction<IDmCqbh> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_DMCQBH_LIST,
    payload: axios.get<IDmCqbh>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IDmCqbh> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DMCQBH,
    payload: axios.get<IDmCqbh>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IDmCqbh> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DMCQBH,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDmCqbh> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DMCQBH,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDmCqbh> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DMCQBH,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
